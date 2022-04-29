package controladores;

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.text.DateFormat;
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



import utils.*;

	@Controller
	public class inventario {
		
		@Autowired
		inventarioManager inventarioManager;
		
		
		@RequestMapping({"inventario.html"})
		public String roles(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			String pass=(String) sesion.getAttribute("pass");
			
			if(login!=null ){
				List lista = inventarioManager.listar_inventario();
			    model.addAttribute("xlista", lista);
			return "inventario";
			}
			else{
				return "error";
			}
		}
		
		@RequestMapping({"inventario2.html"})
		public String inventario(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			String pass=(String) sesion.getAttribute("pass");
			
			if(login!=null ){
				List lista = inventarioManager.listar_inventario2();
			    model.addAttribute("xlista", lista);
			return "inventario2";
			}
			else{
				return "error";
			}
		}
		
}
		
