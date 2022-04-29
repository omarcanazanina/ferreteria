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
	public class Reportes {
		
		@Autowired
		ReportesManager reportesManager;
		Utilitarios util;
		
		@RequestMapping({"reportes.html"})
		public String vista1(Model model,HttpServletRequest request)  throws IOException  {
			HttpSession sesion=request.getSession();
			String login=(String) sesion.getAttribute("log");
			
			if(login!=null ){
			return "reportes";
			}
			else{
				return "error";
			}
		}
		
		@RequestMapping({"gastosReportesHoy.html"})
		public String reporteHoy(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			
			Date date = new Date();
			
			DateFormat ho = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = ho.format(date);
			model.addAttribute("fecha", fecha);
			List<?> lista = this.reportesManager.crearGastosReportesHoy(fecha);
			model.addAttribute("xlista", lista);
			return "gastosReportesHoy";
		}
		
		@RequestMapping({"ventaReportesHoy.html"})
		public String ventaReportesHoy(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
	
			Date date = new Date();
			
			DateFormat ho = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = ho.format(date);
			model.addAttribute("fecha", fecha);
			List<?> lista = this.reportesManager.crearVentaReportesHoy(fecha);
			model.addAttribute("xlista", lista);
			return "ventaReportesHoy";
		}
		
		@RequestMapping({"compraReportesHoy.html"})
		public String compraReportesHoy(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
	
			Date date = new Date();
			
			DateFormat ho = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = ho.format(date);
			model.addAttribute("fecha", fecha);
			List<?> lista = this.reportesManager.crearCompraReportesHoy(fecha);
			model.addAttribute("xlista", lista);
			
			return "compraReportesHoy";
		}
		//ya esta solo que no recupera el codigo del producto
		@RequestMapping({"productosReportesHoy.html"})
		public String productosreporteHoy(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			Date date = new Date();
			DateFormat ho = new SimpleDateFormat("yyyy-MM-dd");
			String fecha = ho.format(date);
			model.addAttribute("fecha", fecha);
			List<?> lista = this.reportesManager.crearProductosReportesHoy(fecha);
			model.addAttribute("xlista", lista);
			return "productosReportesHoy";
		}
		@RequestMapping({"gastosReportes.html"})
		public String gastosreporte(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			String fecha_ini= request.getParameter("fecha_ini");
			
			String fecha_fin= request.getParameter("fecha_fin");
			
			
			model.addAttribute("fecha_ini", fecha_ini);
			model.addAttribute("fecha_fin", fecha_fin);
			List<?> lista = this.reportesManager.crearGastosReportes(fecha_ini,fecha_fin);
			model.addAttribute("xlista", lista);
			return "gastosReportes";
		}
		
		@RequestMapping({"ventaReportes.html"})
		public String ventaReportes(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
	
			String fecha_ini= request.getParameter("fecha_ini");
			
			String fecha_fin= request.getParameter("fecha_fin");
			
			model.addAttribute("fecha_ini", fecha_ini);
			model.addAttribute("fecha_fin", fecha_fin);
			List<?> lista = this.reportesManager.crearVentasReportes(fecha_ini,fecha_fin);
			model.addAttribute("xlista", lista);
			return "ventaReportes";
		}
		
		@RequestMapping({"compraReportes.html"})
		public String compraReportes(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
	
			String fecha_ini= request.getParameter("fecha_ini");
		
			String fecha_fin= request.getParameter("fecha_fin");
			
			model.addAttribute("fecha_ini", fecha_ini);
			model.addAttribute("fecha_fin", fecha_fin);
			List<?> lista = this.reportesManager.crearComprasReportes(fecha_ini, fecha_fin);
			model.addAttribute("xlista", lista);
			
			return "compraReportes";
		}
		
		@RequestMapping({"productosReportes.html"})
		public String productosreporte(Model model,HttpServletRequest request)  throws IOException, ParseException  {
			HttpSession sesion=request.getSession();
			String fecha_ini= request.getParameter("fecha_ini");
			
			String fecha_fin= request.getParameter("fecha_fin");
	
			model.addAttribute("fecha_ini", fecha_ini);
			model.addAttribute("fecha_fin", fecha_fin);
			List<?> lista = this.reportesManager.crearProductosReportes(fecha_ini,fecha_fin);
			model.addAttribute("xlista", lista);
			return "productosReportes";
		}
		
			
	}
		
   
		
	
