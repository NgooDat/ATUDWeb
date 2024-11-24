package webshop.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Component
public class UploadFile {
	private final ServletContext context;

	@Autowired
	public UploadFile(ServletContext context) {
		this.context = context;
	}

	public String handleFileUpload(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return "Vui lòng chọn file!";
		}

		String[] allowedFileTypes = { "image/jpeg", "image/png", "image/gif", "application/pdf", "application/msword",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document" };

		boolean fileTypeValid = false;
		for (String type : allowedFileTypes) {
			if (type.equals(file.getContentType())) {
				fileTypeValid = true;
				break;
			}
		}

		if (!fileTypeValid) {
			return "Loại file không hợp lệ!";
		}

		// Lấy đường dẫn thư mục để lưu file
		String cvPath = context.getRealPath("/files/" + file.getOriginalFilename());
		File destFile = new File(cvPath);
		file.transferTo(destFile);

		return "Lưu thành công!";
	}
}
