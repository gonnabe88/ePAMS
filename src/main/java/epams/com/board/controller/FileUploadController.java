package epams.com.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import epams.com.config.storage.StorageFileNotFoundException;
import epams.com.config.storage.StorageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/upload")
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}
	
	  @GetMapping("/") 
	  public String listUploadedFiles(Model model) throws IOException {
	  model.addAttribute("files", storageService.loadAll().map( path ->
	  MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
	  "serveFile", path.getFileName().toString()).build().toUri().toString())
	  .collect(Collectors.toList()));
	  
	  return "/common/list"; 
	  }
	 

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
/*
	@PostMapping("/")
	@ResponseBody
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		System.out.println("upload post");
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}
	*/	
	
    @PostMapping("/")
    public @ResponseBody String upload(MultipartHttpServletRequest request, @RequestParam HashMap<String, Object> parameter){
        return "/common/list";
    }

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}