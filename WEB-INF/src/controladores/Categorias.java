package controladores;

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import model.manager.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import org.apache.commons.fileupload.servlet.ServletFileUpload;

//import sun.org.mozilla.javascript.internal.regexp.SubString;


//import com.sun.xml.internal.ws.util.StringUtils;

import utils.*;

	@Controller
	public class Categorias {
		
		@Autowired
		categoriasManager categoriasManager;
		Utilitarios util;
		
		@RequestMapping({"categorias.html"})
		public String categorias(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			
			if(login!=null ){
			
			List<?> lista = this.categoriasManager.listar();
			model.addAttribute("xlista", lista);
			return "categorias";
			}
			else{
				return "error";
			}
		}
			
	}
		
   
		
	
