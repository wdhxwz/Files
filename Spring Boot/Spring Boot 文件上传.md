## Spring Boot 文件上传


	@Controller
	public class FileUploadController {
	
		@RequestMapping("/file")
		public String file() {
			return "file";
		}
	
		@RequestMapping("/uploadFile")
		@ResponseBody
		public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
			if (file.isEmpty()) {
				return "content is empty";
			}
	
			System.out.println("文件名=" + file.getOriginalFilename());
			System.out.println("参数名=" + file.getName());
			System.out.println("文件大小=" + file.getSize());
			System.out.println("文件类型=" + file.getContentType());
	
			BufferedOutputStream outputStream = new BufferedOutputStream(
					new FileOutputStream(new File(file.getOriginalFilename())));
			outputStream.write(file.getBytes());
			outputStream.flush();
			outputStream.close();
	
			return "success";
		}
	
		/**
		 * 多文件上传，主要是使用了MultipartHttpServletRequest和MultipartFile
		 * 
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
		public @ResponseBody String handleFileUpload(HttpServletRequest request) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = multipartHttpServletRequest.getFileMap();
			if (fileMap.isEmpty()) {
				return "content is empty";
			}
		
			BufferedOutputStream stream = null;
			for (String fileName : fileMap.keySet()) {
				MultipartFile file = fileMap.get(fileName);
				if (!file.isEmpty()) {
					System.out.println("文件名=" + file.getOriginalFilename());
					System.out.println("参数名=" + file.getName());
					System.out.println("文件大小=" + file.getSize());
					System.out.println("文件类型=" + file.getContentType());
					
					try {
						stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
						stream.write(file.getBytes());
						stream.close();
					} catch (Exception e) {
						stream = null;
						return "You failed to upload " + " => " + e.getMessage();
					}
				} else {
					return "You failed to upload " + " because the file was empty.";
				}
			}
	
			return "upload successful";
		}
	}

