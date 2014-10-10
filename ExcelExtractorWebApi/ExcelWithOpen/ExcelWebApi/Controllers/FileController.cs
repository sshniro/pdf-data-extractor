using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Diagnostics;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using ExcelBusinessLogicLayer.DataTransferObject;
using System.Threading.Tasks;
using System.IO;
using ExcelBusinessLogicLayer;

namespace ExcelWebApi.Controllers
{
    [RoutePrefix("file")]
    public class FileController : ApiController
    {
        ExcelExtractor extractor = new ExcelExtractor();

        [HttpPost]
        [Route("extract")]
        public async Task<Collection<SheetDTO>> uploadAndGetData()
        {
            Collection<SheetDTO> rtnCollection = new Collection<SheetDTO>();



            // Check if the request contains multipart/form-data.
            if (!Request.Content.IsMimeMultipartContent())
            {
                throw new HttpResponseException(HttpStatusCode.UnsupportedMediaType);
            }

            string root = HttpContext.Current.Server.MapPath("~/App_Data");
            var provider = new MultipartFormDataStreamProvider(root);
            var task = await Request.Content.ReadAsMultipartAsync(provider);

            var fileName = provider.FileData.First().Headers.ContentDisposition.FileName;
            var currentFilePath = provider.FileData.FirstOrDefault().LocalFileName;
            var directoryPath = Path.GetDirectoryName(currentFilePath);
            fileName =  fileName.Trim('"');

            var finalFilePath = Path.Combine(directoryPath, fileName);

            if (File.Exists(finalFilePath))
            {
                File.Delete(finalFilePath);
            }
            Directory.Move(currentFilePath, finalFilePath);

            rtnCollection =  extractor.extractExcelToCellDtos(finalFilePath);


            

            return rtnCollection;

        }
    }
}
