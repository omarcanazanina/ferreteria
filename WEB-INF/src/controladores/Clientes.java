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
	public class Clientes {
		
		@Autowired
		ClientesManager clientesManager;
		
		@RequestMapping({"clientes.html"})
		public String gestionproducto(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			
			if(login!=null ){
			List<?> lista = this.clientesManager.listar_clientes();
		    model.addAttribute("xlista", lista);
			return "clientes";
			}
			else{
				return "error";
			}
		}
		@RequestMapping({"agregar_cliente.html"})
		public String agregar_proveedors(Model model,HttpServletRequest request )  throws IOException  {
			int ci =Integer.parseInt( request.getParameter("ci"));
			String nombres = request.getParameter("nombres");
			int celular =Integer.parseInt( request.getParameter("celular"));
			clientesManager.adicionar_cliente( nombres, ci,celular);
			System.out.println("agrego");
			List<?> lista = this.clientesManager.listar_clientes();
		    model.addAttribute("xlista", lista);
			return "clientes";
		}
		
		@RequestMapping({"modificar_cliente.html"})
		public String modificar_cliente(Model model, HttpServletRequest request)  throws IOException  {
			int codcliente = Integer.parseInt(request.getParameter("codcliente"));
			String nombres = request.getParameter("nombres");
			clientesManager.modificar_cliente(codcliente,nombres);
			List<?> lista = this.clientesManager.listar_clientes();
		    model.addAttribute("xlista", lista);
			return "productos";
		}
		
			
	}
		
   
		
	
