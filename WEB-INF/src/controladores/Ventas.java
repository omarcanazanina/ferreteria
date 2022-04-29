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
	public class Ventas {
		
		@Autowired
		ventasManager ventasManager;
		@Autowired
		UsuariosManager usuariosManager;
		@Autowired
		productosManager productosManager;
		@Autowired
		ClientesManager clientesManager;
		
		@RequestMapping({"ventas.html"})
		public String ventas(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			
			if(login!=null ){
			
			List<?> lista = this.ventasManager.listarventas();
			model.addAttribute("xlista", lista);
			return "ventas";
			}
			else{
				return "error";
			}
		}
		
		@RequestMapping({"nuevaVenta.html"})
		public String nuevaVenta(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			List lista = productosManager.listar_productos();
		    model.addAttribute("xlista", lista);
			return "nuevaVenta";
		}
		@RequestMapping({"agregarVenta.html"})
		public String agregarVenta(Model model, HttpServletRequest request)  throws IOException, ParseException  {
			
			HttpSession sesion=request.getSession();
			String id_usuario=(String) sesion.getAttribute("id_usuario");
			
			Date date = new Date();
			DateFormat ho = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = ho.format(date);
			DateFormat ho2 = new SimpleDateFormat("HH:mm:ss");
			String hora = ho2.format(date);
			String cliente = request.getParameter("cliente");
			String pag =request.getParameter("pago") ;
			sesion.setAttribute("pago", pag);
			int codcliente = 0;
			double montocredito = 0;  
			
			double tottal=Double.parseDouble(request.getParameter("tottal2")) ;
			if(clientesManager.verificarCliente(cliente.toUpperCase())!=1){
				codcliente = clientesManager.agregar_cliente(cliente.toUpperCase());
			}
			else{
				codcliente = clientesManager.obtenerCod(cliente.toUpperCase());
			}
			String login=(String) sesion.getAttribute("log");
			String pass=(String) sesion.getAttribute("pass");
			if (Integer.parseInt(pag) ==1 ) {
				ventasManager.agregar_venta(fecha,id_usuario, codcliente, hora,tottal,Integer.parseInt(pag), montocredito);
				
			}else {
				ventasManager.agregar_venta(fecha,id_usuario, codcliente, hora,montocredito,Integer.parseInt(pag), tottal);
			}
			return "nuevaVenta";

		}
		@RequestMapping({"agregarDetalle.html"})
		public String agregarDetalle(Model model, HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			int pago = Integer.parseInt((String) sesion.getAttribute("pago"));
			//System.out.print("ESTE ES EL PAGO EN AGREGAR DETALLE"+pago);
			int cod =ventasManager.ultimaVenta();
			int producto = Integer.parseInt(request.getParameter("producto"));
			double cantidad = Double.parseDouble(request.getParameter("cantidad"));
			double precio = Double.parseDouble(request.getParameter("precio"));
			ventasManager.detalle_venta(cod, producto, cantidad, precio);
			
			double stock = Double.parseDouble(productosManager.obtenerStock(producto));
			double stockfinal=stock-cantidad;
			double productobs=stockfinal*precio;
			if( pago==1 ){
				double monto = Double.parseDouble(productosManager.sacartotal(producto));
				double caja=Double.parseDouble(ventasManager.recuperar_caja());

				double suma=caja+(precio*cantidad);
				ventasManager.modificar_monto(suma);
				double montototal=monto-(cantidad*precio);
				
				//productosManager.actualizarStock(producto,stockfinal,montototal);
				productosManager.descontar_inventario(producto,stockfinal,productobs);
			}
			else{

				//productosManager.actualizarStock2(producto,stockfinal);
				productosManager.descontar_inventario(producto,stockfinal,productobs);
			}
			
			return "nuevaVenta";

		}
		
		@RequestMapping({"detalleventa.html"})
		public String detalleventa(Model model, HttpServletRequest request)  throws IOException  {
			String cod =request.getParameter("cod");
			List<?> datos= this.ventasManager.listarventaEspecifica(cod);
			List<?> nombres = this.ventasManager.obtenerdetalle(Integer.parseInt(cod));
			model.addAttribute("datos", datos);
			model.addAttribute("nombres", nombres);
			
			return "detalleventa";
		}
	}
		
   
		
	
