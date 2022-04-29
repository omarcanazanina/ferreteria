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
//import sun.org.mozilla.javascript.internal.regexp.SubString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//import com.sun.xml.internal.ws.util.StringUtils;

import org.apache.commons.fileupload.servlet.ServletFileUpload;


import utils.*;

	@Controller
	public class Usuarios {
		
		@Autowired
		UsuariosManager usuariosManager;
		Utilitarios util;
		
		@RequestMapping({"usuarios.html"})
		public String vista1(Model model,HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			
			if(login!=null ){
				List<?> lista = this.usuariosManager.listarUsuarios();
			    model.addAttribute("xlista", lista);
			return "usuarios";
			}
			else{
				return "error";
			}
			
		}
		
		@RequestMapping({"agregar_usuario.html"})
		public String agregarusuario(Model model,HttpServletRequest request )  throws IOException  {
		
			String nombre = request.getParameter("nombre");
			String apellidos = request.getParameter("apellidos");	
			String login = request.getParameter("login");
			String pass = request.getParameter("pass");
			int id = usuariosManager.ultimoId()+1;
			usuariosManager.agregarUsuario(id ,nombre, apellidos, login, pass);
			return "usuarios";
		}
		@RequestMapping({"eliminarUsuario.html"})
		public String eliminar_persona(Model model,HttpServletRequest request )  throws IOException  {
			String ci = request.getParameter("ci");
			
			usuariosManager.eliminarUsuario(ci);
			return "usuarios";
		}
		@RequestMapping({"habilitarUsuario.html"})
		public String habilitar_persona(Model model,HttpServletRequest request )  throws IOException  {
			
			String ci = request.getParameter("ci");
			usuariosManager.habilitarUsuario(ci);
			
			return "usuarios";
		}
		
		@RequestMapping({"modificar_usuario.html"})
		public String modificarusuario(Model model,HttpServletRequest request )  throws IOException  {
			String ci = request.getParameter("ci");
			String nombre = request.getParameter("nombre");
			String apellidos = request.getParameter("apellidos");	
			String rol = request.getParameter("rol");
			usuariosManager.modificarUsuario(ci, nombre, apellidos);
			return "usuarios";
		}
		
		@RequestMapping({"cambiarPass2.html"})
		public String cambiarpass2(Model model,HttpServletRequest request )  throws IOException  {
			String pass = request.getParameter("pass");
			String login = request.getParameter("login");
			String id = request.getParameter("ci");
			usuariosManager.modificarpass2(login,pass,id);
			return "usuarios";
				
		}
		
		@ResponseBody
		@RequestMapping({"comprobarpass.html"})
		public String comprobarpass(Model model,HttpServletRequest request )  throws IOException  {
			String pass = request.getParameter("pass-actual");
			
			String enviar = String.valueOf(usuariosManager.comprobarpass(pass));
			
			return enviar;
				
		}
		@RequestMapping({"cambiarPass.html"})
		public String cambiarPass(Model model,HttpServletRequest request )  throws IOException  {
			String login = request.getParameter("login-actual");
			String pass = request.getParameter("pass-nuevo");
			
			
			usuariosManager.modificarpass(login,pass);
			return "usuarios";
				
		}
		
		@RequestMapping({"cambiarCaja.html"})
		public String cambiarCaja(Model model,HttpServletRequest request )  throws IOException  {
			String caja = request.getParameter("caja-actual");
			usuariosManager.modificarCaja(caja);
			return "usuarios";
				
		}
			
	}
		
   
		
	
