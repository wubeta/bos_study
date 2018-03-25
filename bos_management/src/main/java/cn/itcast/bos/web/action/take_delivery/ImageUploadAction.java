package cn.itcast.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.web.action.common.BaseAction;


@Controller
@Namespace("/")
@Scope("prototype")
@ParentPackage("json-default")
public class ImageUploadAction extends BaseAction<Object>{

	private File imgFile;
	private String imgFileFileName;
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}


	@Action(value="image_upload",results={@Result(name="success",type="json")})
	public String imageFileUpload() throws IOException{
//		System.out.println(imgFileFileName);
		
		
		
		String path = ServletActionContext.getServletContext().getRealPath("/upload/");
		System.out.println(path);
		
		String url = ServletActionContext.getRequest().getContextPath()+"/upload/";
		
		UUID uuid = UUID.randomUUID();
		String fileName = uuid+imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
		
		FileUtils.copyFile(imgFile, new File(path+"/"+fileName));
		
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("error", 0);
		m.put("url",url+fileName);
		
		ActionContext.getContext().getValueStack().push(m);
		
		return SUCCESS;
	}
	
	@Action(value="image_manage",results={@Result(name="success",type="json")})
	public String manage(){
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String rootPath = ServletActionContext.getServletContext().getRealPath("/") + "upload/";
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl  = ServletActionContext.getRequest().getContextPath() + "/upload/";
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		
		
		File currentPathFile = new File(rootPath);
		
		
		List<HashMap<String,Object>> fileList = new ArrayList<HashMap<String,Object>>();
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				HashMap<String, Object> hash = new HashMap<String, Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", "");
		result.put("current_dir_path", rootPath);
		result.put("current_url", rootUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		
		ActionContext.getContext().getValueStack().push(result);
		
		return SUCCESS;
	}
	
	
	
}
