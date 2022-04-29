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
	public class SaldosCompras {
		
		@Autowired
		SaldosComprasManager SaldosComprasManager;
		@Autowired
		GastosManager GastosManager;
		@Autowired
		SaldosManager SaldosManager;
		
		@RequestMapping({"saldos1.html"})
		public String roless(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");	
				List<?> lista = this.SaldosComprasManager.listar_saldoscompras();
				model.addAttribute("xlista", lista);
				return "saldos1";
	
		}
		@RequestMapping({"modificar_saldo1.html"})
		public String modificarsaldo(Model model,HttpServletRequest request )  throws IOException  {
			int idsaldo =Integer.parseInt(request.getParameter("idsaldo") );
			double monto= Double.parseDouble(request.getParameter("monto"));
			double pago =Double.parseDouble(request.getParameter("pago")) ;
			double saldoactual=monto-pago;
			double caja =GastosManager.valorCaja();
			double paguito=caja-pago;
			SaldosComprasManager.modificarsaldo(idsaldo,saldoactual);
		
			SaldosComprasManager.modificarCaja(paguito);
			
			List<?> lista = this.SaldosComprasManager.listar_saldoscompras();
			model.addAttribute("xlista", lista);
			return "saldos1";
		}
		
		@RequestMapping({"llenarCaja1.html"})
		@ResponseBody
		public String llenarCaja(Model model, HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String nombre =SaldosComprasManager.llenarCaja1().toString().replace("[{", "").replace("}]", "");
			return nombre;
		}
		
}
		
