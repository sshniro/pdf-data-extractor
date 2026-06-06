# PDF Data Extractor

A template-based **PDF data-extraction web application**, built in 2014–2015 to pull structured
data out of fixed-layout PDF documents — originally for the **garment / apparel-export sector in
Sri Lanka** (export declarations, packing lists, invoices and similar forms that arrive as PDFs
with a stable layout).

Instead of writing bespoke parsing code per document type, a user **marks up regions on a sample
PDF once** (text fields, tables, images, regex/pattern blocks), the markup is saved as a reusable
**template**, and every subsequent PDF of that type is extracted automatically against the
template. Results are stored in MongoDB and exported to Excel.

> **Status:** archived 2014–2015 project, kept as a reference / portfolio implementation. It is
> **not production-hardened** — see [Security notice](#security-notice) before running it anywhere
> public. The original hand-written code is preserved as-is.

---

## Background & motivation

This was written in late 2014. At that time, doing **repeatable, template-driven structured
extraction** from fixed-layout PDFs — on your own servers, with no per-document coding — had no
convenient off-the-shelf answer:

- **Managed document-AI services did not exist yet.** Amazon Textract (2019), Azure Form
  Recognizer / Document Intelligence (2019–2020) and Google Document AI (2020–2021) all came
  *years* later.
- **Mature open-source table libraries came after this project started.** `pdfplumber` first
  appeared on PyPI in Aug 2015 and `Camelot` in 2018; this project began in Oct 2014.
- **Apache PDFBox 1.8.6** (the engine used here) gives you raw text plus per-glyph coordinates —
  but **no table or form semantics**. Any notion of "this region is a column / this is a labelled
  field" had to be built by hand.

Ad-hoc tools did exist (Tabula, released 2013, for one-off table pulls; commercial capture suites
like ABBYY/Kofax), so it would be an overstatement to say *nothing* could read a PDF. But for a
self-hosted Java shop that needed to extract the **same fields from many same-layout documents,
repeatedly, with storage and Excel output**, building a coordinate-template engine on PDFBox +
MongoDB was a reasonable build-vs-buy decision. That engine is what this repository is.

See [Is there a better way today?](#is-there-a-better-way-today) for what you would reach for now.

---

## How it works

The system has two phases: **template markup** (done once per document type) and **extraction**
(done for every incoming document).

```
TEMPLATE MARKUP (once per document type)
  Upload sample PDF ──▶ each page rendered to a JPEG preview (PDFBox)
        │
        ▼
  User draws regions on the preview in the browser (imgAreaSelect):
  text · table · image · regex · pattern  ──▶ region coordinates + rules
        │  (AJAX, Knockout.js)
        ▼
  Saved as a TEMPLATE in MongoDB (templateInfo collection)

EXTRACTION (per incoming PDF)
  Upload PDF ──▶ load matching template from MongoDB
        │
        ▼
  Rescale each region's image-space coords ──▶ PDF user-space coords
        │
        ▼
  Run the matching extractor with Apache PDFBox:
    text   → PDFTextStripperByArea over the region rectangle
    table  → TextLocationRetriever + a cursor state machine over glyph positions
    image  → PDPage.setCropBox + render region to JPEG
    regex/pattern → delimiter-based string extraction over region text
        │
        ▼
  Store results in MongoDB (extractedData) ──▶ export to Excel (Apache POI)
```

The interesting/fiddly part is the **coordinate mapping**: regions are marked in *image pixels*
(top-left origin) on the rendered preview, and rescaled at extraction time to *PDF user space*
(bottom-left origin, 1/72") using the ratio of preview-image to PDF-page dimensions, with special
handling for 90°/270° page rotations.

---

## Architecture

| Layer | Technology | Role |
|---|---|---|
| Frontend | JSP + Bootstrap 3, **Knockout.js**, jQuery, `imgAreaSelect`, `jsTree` | Visual region-markup canvas, template tree, AJAX to the servlets |
| Web / controllers | Java **Servlets** (`com.data.extractor.controllers`) | Thin request parsing; delegate to model classes |
| Business logic | Java model classes (`com.data.extractor.model.*`) | Markup processing, extraction orchestration, extractors |
| PDF engine | **Apache PDFBox 1.8.6** | Text/coordinate extraction, PDF→image rendering, region cropping |
| Storage | **MongoDB 2.10.1** (db `staging`) | Templates, markup, extracted data, users, dictionary, counters |
| Excel output | **Apache POI 3.9** (`XSSFWorkbook`) | Generates the downloadable `.xlsx` from extracted data |
| Excel API (separate) | **C# / ASP.NET Web API**, `DocumentFormat.OpenXml` | A standalone Excel reader service (see note below) |

**Key servlets** (see `web/WEB-INF/web.xml`):
`TemplateUploadController` (upload sample PDF) · `MarkUpTemplateRegionController` (save region
markup) · `ExtractPdfController` (run extraction) · `ExcelExtractController` (generate `.xlsx`) ·
`ManageCategoriesController` / `DictionaryController` / `ManageUsersController` / `SessionController`.

**MongoDB collections:** `templates` (category/template tree), `templateInfo` (region markup),
`extractedData` (results), `users`, `dictionary` (field schema), `counter` (id sequences),
`xFiles` (uploaded-file metadata). `MongoDBContextListener` builds one shared `MongoClient` at
startup from the `MONGODB_HOST` / `MONGODB_PORT` context params in `web.xml` and stores it on the
`ServletContext`.

> **Note on the C# service (`ExcelExtractorWebApi/`):** despite the folder name "ExcelWithOpen",
> it uses Microsoft's `DocumentFormat.OpenXml` SDK (not EPPlus). It exposes a `POST /file/extract`
> endpoint that reads an uploaded `.xlsx` into cell DTOs. It is **not wired into the main
> extraction flow** — the live Excel *output* path is the Java Apache POI code
> (`ExcelExtractController` → `ExcelFileGenerator`). Treat the C# project as a standalone /
> experimental utility.

---

## Repository layout

```
.
├── src/                     # Java source — com.data.extractor (controllers, model, beans, DAOs, extractors)
├── web/                     # Web app: JSP views, WEB-INF/web.xml, assets/ (JS/CSS)
│   └── src/                 # ⚠ STALE duplicate of the Java source — see Housekeeping
├── lib/                     # Vendored dependency JARs (no build tool — these are load-bearing)
├── ExcelExtractorWebApi/    # Separate C# / ASP.NET Web API (Excel reader)
└── dev_extractor.iml        # IntelliJ IDEA module (the only "build" definition)
```

---

## Build & run

There is **no build tool** (no Maven/Gradle/Ant). The project is an **IntelliJ IDEA** module whose
classpath is the vendored `lib/*.jar`.

**Prerequisites:** JDK 7/8, a Servlet 3.1 container (e.g. Tomcat 8), and MongoDB.

1. **MongoDB** — run it locally on `localhost:27017`. The app uses the database named `staging`
   (this name is currently hard-coded in the DAOs).
2. **Open in IntelliJ** using `dev_extractor.iml`; the module already references `lib/*.jar`.
   Source root is `src/`, web root is `web/`.
3. **Configure host/port** if needed via `web/WEB-INF/web.xml` (`MONGODB_HOST`, `MONGODB_PORT`).
4. **Build a WAR** (compile `src/` → `web/WEB-INF/classes`, bundle `web/` + `lib/`) and deploy to
   Tomcat. Open `index.jsp`.

**C# Excel API (optional):** open `ExcelExtractorWebApi/ExcelWithOpen/` in Visual Studio (targets
.NET 4.5.1, ASP.NET MVC/Web API 5.1) and run with IIS Express. It is independent of the Java app.

---

## Known limitations

These are properties of the 2014 coordinate/template design and are documented, not patched:

- **Coordinate fragility.** Region coordinates are tied to the preview image rendered at markup
  time. If a document is re-rendered at a different DPI, or uses different fonts/generators, the
  stored coordinates can misalign.
- **Table extraction is a cursor state machine** that assumes left-to-right, top-to-bottom text
  flow. Multi-column layouts, rotated text, RTL, or tables that span pages are not handled well.
- **"Regex" extraction is delimiter string-splitting**, not a real regex engine — if the content
  contains the delimiter, or a delimiter spans lines, it fails silently.
- **Page-rotation handling is incomplete** (90°/270° are handled in places; there are `TODO`s for
  others; the table path applies no rotation transform).
- **Encrypted PDFs** are only handled for an empty password.
- **Float/double coordinate comparisons** near region edges can cause boundary-detection misses.

---

## Is there a better way today?

Yes — the same job is now much easier. For new work you would reach for one of:

- **Open source (native PDFs):** [`pdfplumber`](https://github.com/jsvine/pdfplumber) (geometry +
  word/table extraction — the closest modern analogue to this repo's coordinate templates),
  [`Camelot`](https://github.com/camelot-dev/camelot) / `tabula-py` (ruled-table extraction),
  [`PyMuPDF`](https://github.com/pymupdf/PyMuPDF) (`find_tables()`).
- **Open source (layout/ML, on-prem):** [IBM Docling](https://github.com/docling-project/docling),
  Microsoft Table Transformer / PaddleOCR PP-Structure,
  [`unstructured`](https://github.com/Unstructured-IO/unstructured) for LLM pipelines.
- **Managed document-AI:** AWS Textract, Google Document AI (Form/Layout/Custom Extractor — the
  modern equivalent of "templates"), Azure AI Document Intelligence.
- **Multimodal LLMs:** Claude, Gemini, GPT-4o read the page and emit typed JSON against a schema —
  resilient to layout drift (validate numeric fields).

This repository is kept as the **original 2014–2015 implementation**; none of the above existed in
its accessible/managed form when it was built.

---

## Security notice

This was a 2014–2015 academic / early-career project and is **not safe to deploy publicly as-is**.
Known issues (documented here, intentionally not rewritten):

- **Passwords are stored and compared in plaintext** (`UsersDAO`).
- **`admin`/`admin` default-credential bypass** — logging in as `admin`/`admin` when no such user
  exists silently provisions an admin account (`LoginRequestProcessor`).
- **Source-code disclosure:** `web/src/` ships a (stale) duplicate of the Java source *inside the
  web root*, so a deployed WAR would serve the auth code as static files. It should be removed.
- **No central auth filter** across action servlets; **uploads are written to disk before
  validation**.
- **End-of-life dependencies with known CVEs:** PDFBox 1.8.6, MongoDB driver 2.10.1, POI 3.9,
  `commons-fileupload` 1.3, `dom4j` 1.6.1 (XXE), `gson` 2.2.4.

If you ever revive this, treat hashing passwords (bcrypt/Argon2), removing the `admin/admin`
branch, deleting `web/src/`, adding an auth filter, and upgrading dependencies as prerequisites.

---

## Housekeeping notes

For maintainers (see also the expanded `.gitignore`):

- A large amount of **build output is committed** (`ExcelExtractorWebApi/` alone is ~46 MB of
  `bin/`, `obj/`, NuGet `packages/`, `.dll`/`.pdb`/`.exe`, `.suo`/`.user`), plus 14 `.DS_Store`
  files and the IntelliJ `dev_extractor.iml`. None of it is source and it can be untracked.
- **`web/src/`** is a stale duplicate of `src/` (also a security issue — see above).
- **Real customer/business data** is committed under `ExcelExtractorWebApi/.../App_Data/` and
  should be purged from history.
- **Scratch/experimental code** exists (`model/top/secret/`, `controllers/Testing*.java`, stray
  `test.java`, `*_copy.jsp`). Note: the package named `model/testing/` is **not** all scratch —
  `ExcelFileGenerator`/`ExcelGenerator` there are used by live controllers, so do not blanket-delete it.
- There is **no build file** — consider adding Maven/Gradle to pin the `lib/*.jar` versions.

---

## License

Released under the [Apache License 2.0](./LICENSE).
