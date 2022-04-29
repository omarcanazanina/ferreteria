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





import utils.*;

	@Controller
	public class Proveedor {
		
		@Autowired
		ProveedorManager ProveedorManager;
		Utilitarios util;
		@RequestMapping({"proveedores.html"})
		public String tipos(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			if(login!=null ){
			List<?> listita = this.ProveedorManager.listar_provee();
		    model.addAttribute("xlista", listita);
			return "proveedores";
			}
			else{
				return "error";
			}
		}
		
		@RequestMapping({"agregar_proveedor.html"})
		public String agregar_proveedor(Model model,HttpServletRequest request )  throws IOException  {
			String ciproveedor = request.getParameter("ciproveedor");
			String nombres = request.getParameter("nombres");
			String celular = request.getParameter("celular");
			System.out.println(ciproveedor+nombres);
			ProveedorManager.adicionar_proveedor(ciproveedor, nombres,Integer.parseInt(celular));
			System.out.println("agrego");
			List<?> listita = this.ProveedorManager.listar_provee();
		    model.addAttribute("xlista", listita);
			return "proveedores";
		}
		
		@RequestMapping({"modificar_proveedor.html"})
		public String modificarusuario(Model model,HttpServletRequest request )  throws IOException  {
			String id = request.getParameter("id1");
			String ci = request.getParameter("ci1");
			String nombres = request.getParameter("nombres1");
			String celular = request.getParameter("celular1");
			ProveedorManager.modificarProveedor(ci, nombres,Integer.parseInt(celular),Integer.parseInt(id));
			return "proveedores";
		}
			
	}
		