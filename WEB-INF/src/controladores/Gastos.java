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
	public class Gastos {
		
		@Autowired
		GastosManager GastosManager;
		
		
		@RequestMapping({"gastos.html"})
		public String roless(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			if(login!=null ){
				
				List<?> lista = this.GastosManager.listar_gastos();
				model.addAttribute("xlista", lista);
				return "gastos";
				}
				else{
					return "error";
				}
		}
		
		@RequestMapping({"agregargasto.html"})
		public String agregarVenta(Model model, HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			String id_usuario=(String) sesion.getAttribute("id_usuario");
			Date date = new Date();
			String login=(String) sesion.getAttribute("log");
			DateFormat ho = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = ho.format(date);
			DateFormat formato1=new SimpleDateFormat("HH:mm:ss");
			String hora =formato1.format(date);

			
			String descripcion = request.getParameter("descripcion");
			double monto =Double.parseDouble(request.getParameter("monto"));
			
			GastosManager.agregar_gasto(fecha,id_usuario, login, descripcion, monto, hora);
			
			double caja =GastosManager.valorCaja();
		
			GastosManager.restarmonto(monto,caja);
			List<?> lista = this.GastosManager.listar_gastos();
		    model.addAttribute("xlista", lista);
			return "gastos";

		}
		@RequestMapping({"modificar_gasto.html"})
		public String modificarusuario(Model model,HttpServletRequest request )  throws IOException  {
			int idgasto =Integer.parseInt(request.getParameter("idgasto") );
			String descripcion = request.getParameter("descripcion");
			double monto =Double.parseDouble(request.getParameter("monto")) ;
			double monto1= Double.parseDouble(request.getParameter("monto1"));
			double caja =GastosManager.valorCaja();
			GastosManager.sumarmonto(monto1,caja);
			double caja1 =GastosManager.valorCaja();
			GastosManager.restarmonto(monto,caja1);
			GastosManager.modificarGasto(idgasto, descripcion, monto);
			List<?> lista = this.GastosManager.listar_gastos();
		    model.addAttribute("xlista", lista);
			return "gastos";
		}
		
}
		
