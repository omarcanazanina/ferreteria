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
	public class Compras {
		
		@Autowired
		ComprasManager ComprasManager;
		@Autowired
		productosManager productosManager;
		Utilitarios util;
		
		@RequestMapping({"compras.html"})
		public String tipos(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			if(login!=null ){
			List<?> listita = this.ComprasManager.listar();
			List lista = productosManager.listar_productos();
		    model.addAttribute("productos", lista);
		    model.addAttribute("xlista", listita);
			return "compras";
			}
			else{
				return "error";
			}
		}
		@RequestMapping({"listarcompras.html"})
		public String listarcompras(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			List<?> listita = this.ComprasManager.listarcompras();
		    model.addAttribute("xlista", listita);
			return "listarcompras";
		}
		
		@RequestMapping({"detallecompra.html"})
		public String listardetalle(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String cod =  request.getParameter("cod");
			List<?> datos = this.ComprasManager.listarcompraEspecifica(cod);
			List<?> detalle = this.ComprasManager.listardetalle(cod);
		    model.addAttribute("datos", datos);
		    model.addAttribute("detalle", detalle);
			return "detallecompra";
		}
		
		@RequestMapping({"agregarCompra.html"})
		public String agregarCompra(Model model, HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			
			String id_usuario=(String) sesion.getAttribute("id_usuario");
			
			String pag =request.getParameter("pago") ;
			sesion.setAttribute("pago", pag);
			String inv =request.getParameter("inventario") ;
			sesion.setAttribute("inventario", inv);
			
			String proveedor= request.getParameter("proveedor");
			String fecha= request.getParameter("fecha") ;
			int factura=Integer.parseInt(request.getParameter("factura"));
			String nombre = request.getParameter("nombre");
			double tottal=Double.parseDouble(request.getParameter("tottal2")) ;
			
			ComprasManager.agregar_compra(proveedor,id_usuario, nombre, fecha, factura,tottal,Integer.parseInt(pag));
			
			return "compras";

		}
		
		@RequestMapping({"agregarDetalle1.html"})
		public String agregarDetalle(Model model, HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			
			int pago = Integer.parseInt((String) sesion.getAttribute("pago"));
			String inventario = (String) sesion.getAttribute("inventario");
			
			int cod =ComprasManager.ultimaCompra();
			
			int producto = Integer.parseInt(request.getParameter("producto"));
			double cantidad = Double.parseDouble(request.getParameter("cantidad"));
			double precio = Double.parseDouble(request.getParameter("precio"));
			
			ComprasManager.detalle_compra(producto, cod, cantidad, precio);
		
			double stock = Double.parseDouble(productosManager.obtenerStock(producto));
			double monto = Double.parseDouble(productosManager.sacartotal(producto));
			double stockfinal=stock+cantidad;
			
			double montototal=monto+(cantidad*precio);
			
			double productobs=stockfinal*precio;
		
			
		
			productosManager.actualizarStock(producto,stockfinal,montototal);
			if (pago==1) {
				//CAJA
				System.out.println("ENTRO AL IF AL CONTADO");
				double caja=Double.parseDouble(ComprasManager.recuperar_caja());
				System.out.println("venta es "+precio+" "+cantidad);
				double resta=caja-(precio*cantidad);
				ComprasManager.modificar_monto(resta);
				if (inventario.equals("Tienda")) {
					productosManager.descontar_inventario(producto,stockfinal,productobs);
					return "compras";
				}
				else{
					productosManager.modificar_inventariocasa(producto,stockfinal,productobs);
					return "compras";
				}
			}
			else {
				System.out.println("ENTRO AL IF AL FIO");
				if (inventario.equals("Tienda")) {
					productosManager.descontar_inventario(producto,stockfinal,productobs);
					return "compras";
				}
				else{
					productosManager.modificar_inventariocasa(producto,stockfinal,productobs);
					return "compras";
				}
			}
		

		}
			
	}
		
   
		
	
