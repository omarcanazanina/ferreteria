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
import javax.servlet.ServletException;
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
	public class Productos {
		
		@Autowired
		productosManager productosManager;
		
		
		@RequestMapping({"productos.html"})
		public String vista1(Model model,HttpServletRequest request)  throws IOException, Exception,ServletException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			String pass=(String) sesion.getAttribute("pass");
			
			if(login!=null ){
				List lista = productosManager.listar_productos();
			    model.addAttribute("xlista", lista);
			return "productos";
			}
			else{
				return "error";
			}
		}
		
		@RequestMapping({"agregar_producto.html"})
		public String agregar_producto(Model model,HttpServletRequest request )  throws IOException  {
			int codproducto = Integer.parseInt(request.getParameter("codproducto"));
			double punit =Double.parseDouble(request.getParameter("punit"));
			String nombre = request.getParameter("nombre");
			double cantidad =Double.parseDouble(request.getParameter("cantidad"));
			String inventario= request.getParameter("inventario");
			//agre preferencia
			double pref =Double.parseDouble(request.getParameter("pref"));
			//
			double monto=punit*cantidad;
				productosManager.adicionar_pro(codproducto,nombre,punit,cantidad,monto,inventario,pref);
			if (inventario.equals("Tienda")) {
				
				productosManager.adicionar_producto(codproducto,punit, cantidad,monto,pref);
				productosManager.adicionar_producto2(codproducto,punit,0,0,pref);
				List<?> lista = this.productosManager.listar_productos();
				model.addAttribute("xlista", lista); 
				return "productos";
			}
			else{
				productosManager.adicionar_productoinv(codproducto,punit, cantidad,monto,pref);
				productosManager.adicionar_producto3(codproducto,punit,0,0,pref);
				List<?> lista = this.productosManager.listar_productos();
				model.addAttribute("xlista", lista); 
				return "productos";
			}
		}
		@RequestMapping({"modificar_producto.html"})
		public String modificar_producto(Model model, HttpServletRequest request)  throws IOException  {
			
			int codproducto = Integer.parseInt(request.getParameter("idproducto"));
			double punit =Double.parseDouble(request.getParameter("punit"));
			String nombre = request.getParameter("nombre");
			double cantidadtienda =Double.parseDouble(request.getParameter("cantidadtienda"));
			double cantidadcasa =Double.parseDouble(request.getParameter("cantidadcasa"));
			String inventario= request.getParameter("inventario");
			double pref =Double.parseDouble(request.getParameter("pref"));
			double montotienda=cantidadtienda*punit;
			double montocasa=cantidadcasa*punit;
			productosManager.modificar_productos(codproducto,nombre);
				productosManager.modificar_productostienda(codproducto,punit,cantidadtienda,montotienda,pref);
				productosManager.modificar_productoscasa(codproducto,punit,cantidadcasa,montocasa,pref);
				List<?> lista = this.productosManager.listar_productos();
				model.addAttribute("xlista", lista); 
				return "productos";
			
		}
		@RequestMapping({"transferir_producto.html"})
		public String transferir1(Model model, HttpServletRequest request)  throws IOException  {
			System.out.println("entro");
			int codproducto = Integer.parseInt(request.getParameter("idproducto6"));
			double cantidad6 =Double.parseDouble(request.getParameter("cantidad6"));
			double cantidad5 =Double.parseDouble(request.getParameter("cantidad5"));
			double punit =Double.parseDouble(request.getParameter("punit6"));
			
		
			String inventario= request.getParameter("inventario6");
			
			
			if (cantidad6>=cantidad5 ) {
			System.out.println("entro al if");
				double cantidadtotal=cantidad6-cantidad5;
				double sacarmontototaltienda=cantidadtotal*punit;
				
				double stockcasa=Double.parseDouble(productosManager.recuperarstockCasa(codproducto));
				
				productosManager.descontar_inventario(codproducto,cantidadtotal,sacarmontototaltienda);
				double stockcasatotal=stockcasa+cantidad5;
				
				double sacarmontototal=stockcasatotal*punit;
				productosManager.modificar_inventariocasa(codproducto,stockcasatotal,sacarmontototal);
			}
			else {
				System.out.println("no se puede");
			}
			return "productos";
		}
		@RequestMapping({"transferir_producto2.html"})
		public String transferir12(Model model, HttpServletRequest request)  throws IOException  {
			
			int codproducto = Integer.parseInt(request.getParameter("idproducto9"));
			double cantidad9 =Double.parseDouble(request.getParameter("cantidad9"));
			double cantidad8 =Double.parseDouble(request.getParameter("cantidad8"));
			double punit =Double.parseDouble(request.getParameter("punit9"));
			
			
			String inventario= request.getParameter("inventario6");
			
			
			if (cantidad9>=cantidad8 ) {
			System.out.println("entro al if");
				double cantidadtotal=cantidad9-cantidad8;
				double sacarmontototalcasa=cantidadtotal*punit;
				
				double stocktienda=Double.parseDouble(productosManager.recuperarstockTienda(codproducto));
			
				productosManager.actualizarstock_casa(codproducto,cantidadtotal,sacarmontototalcasa);
				double stocktiendatotal=stocktienda+cantidad8;
				
				double sacarmontototal=stocktiendatotal*punit;
				productosManager.modificar_inventariotienda(codproducto,stocktiendatotal,sacarmontototal);
			}
			else {
				System.out.println("no se puede");
			}
			return "productos";
		}
		@RequestMapping({"obtenerProducto.html"})
		@ResponseBody
		public String obtenerProducto(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			int codpro=Integer.parseInt(request.getParameter("codpro"));
			String nombre =productosManager.obtenerProducto(codpro).toString().replace("[{", "").replace("}]", "");
			System.out.println("este es"+ nombre);
			return nombre;
		}
		@RequestMapping({"obtenerProducto2.html"})
		@ResponseBody
		public String obtenerProducto2(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			int codpro=Integer.parseInt(request.getParameter("codpro"));
			String nombre =productosManager.obtenerProducto2(codpro).toString().replace("[{", "").replace("}]", "");
			return nombre;
		}
		@RequestMapping({"eliminar_producto.html"})
		@ResponseBody
		public String eliminar_productos(Model model, @RequestParam String codproducto, HttpServletRequest request)  throws IOException  {
				
			productosManager.eliminar_producto(codproducto);
			return "productos";
		}
		@RequestMapping({"habilitar_producto.html"})
		@ResponseBody
		public String habilitar_productos(Model model, @RequestParam String codproducto, HttpServletRequest request)  throws IOException  {
				
			productosManager.habilitar_productos(codproducto);
			return "productos";
		}
		
		@RequestMapping({"verificarProducto.html"})
		@ResponseBody
		public String verificarProducto(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String pro =request.getParameter("codproducto");
			String nombre =String.valueOf(productosManager.verificarProducto(pro));
			return nombre;
		}
		
		@RequestMapping({"verificarCantidad.html"})
		@ResponseBody
		public String verificarCantidad(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String cant =request.getParameter("codpro");
			
			String cantidad =String.valueOf(productosManager.verificarCantidad(cant));
			
			return cantidad;
		}
		
	}
		
   
		
	
