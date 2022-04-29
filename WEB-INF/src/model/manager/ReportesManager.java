package model.manager;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

//@Service indica que la clase es un bean de la capa de negocio
@Service
public class ReportesManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listar(){
		String xsql="select * from reportes_ventas ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	
	public int crear_reporte(String fecha_actual) throws ParseException  {
		int tprod=0;
		int totale=0;
		SimpleDateFormat dat = new SimpleDateFormat("yyy-MM-dd");
		Date fecha_a = dat.parse(fecha_actual);
		String xsql="insert into reportes_ventas(fechareporte,total_productos,total_efectivo) values(?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {fecha_a,tprod,totale});
	}
	
	public int modificarPersona(String xcodper,String xnombre,String xap,String xam, String xgenero,String xtipoper,String xfoto){
		String xsql=" update personas set nombre=?, ap=?, am=? ,genero=?, tipoper=?,foto=? where codper=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {xnombre.toUpperCase(),xap.toUpperCase(),xam.toUpperCase(),xgenero.toUpperCase(),xtipoper,Integer.parseInt(xcodper),xfoto,Integer.parseInt(xcodper) });
	}

	public int acceso_datos(String xlogin, String xclave){
		String xsql="select count(*)"+
	                 " from usuarios"+
				     " where login=? and pass=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {xlogin, xclave});
	}
	
	public List<Map<String,Object>> reporteventas(String fechaini,String fechafin){
		String xsql="select v.*, u.nombres as nombres_usu, u.apellidos as ap_usu from ventas v, usuarios u where (v.fechaventa between ? and ?) and v.id_usuario=u.id))";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fechaini, fechafin});
	}
	public List<Map<String,Object>> crearGastosReportesHoy(String fecha) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech = dat.parse(fecha);
		String xsql="select g.*, u.nombres as nombre_usu, u.apellidos as ap_usu from gastos g, usuarios u where fechagasto=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech});
	}
	public List<Map<String,Object>> crearVentaReportesHoy(String fecha) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech = dat.parse(fecha);
		System.out.println("ventas"+fech);
		String xsql="select v.idventa, v.fechaventa, v.horaventa, v.total, v.estado, c.nombres,u.nombres as nombre_usu, u.apellidos as ap_usu from ventas v, usuarios u, "+
				 "clientes c where v.codcliente=c.codcliente and fechaventa = ? and v.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech});
	}
	public List<Map<String,Object>> crearCompraReportesHoy(String fecha) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech = dat.parse(fecha);
		System.out.println(fech);
		String xsql="select c.idcompra, c.nombrecomprador, c.fechacompra, p.nombres, c.total,u.nombres as nombre_usu, u.apellidos as ap_usu from compras c,usuarios u, "+
				 "proveedores p where  c.ciproveedor=p.ciproveedor and fechacompra=? and c.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech});
	}
	public List<Map<String,Object>> crearProductosReportesHoy(String fecha) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech = dat.parse(fecha);
		String xsql="select p.idproducto, p.nombre,sum(d.cantidad) as total from productos p, detventa d, ventas v where p.idproducto=d.idproducto and v.idventa=d.idventa  and v.fechaventa=? group by p.idproducto ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech});
	}
	
	public List<Map<String,Object>> crearGastosReportes(String fecha_ini, String fecha_fin) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech_ini = dat.parse(fecha_ini);
		Date fech_fin = dat.parse(fecha_fin);
		String xsql="select g.*, u.nombres as nombre_usu, u.apellidos as ap_usu from gastos g,usuarios u where fechagasto between ? and ? and g.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech_ini, fech_fin});
	}
	
	public List<Map<String,Object>> crearVentasReportes(String fecha_ini, String fecha_fin) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech_ini = dat.parse(fecha_ini);
		Date fech_fin = dat.parse(fecha_fin);
		String xsql="select v.idventa, v.fechaventa, v.horaventa, v.total,v.estado, c.nombres, u.nombres as nombre_usu, u.apellidos as ap_usu from ventas v, "+
				 "clientes c,usuarios u where v.codcliente=c.codcliente and fechaventa between ? and ? and v.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech_ini, fech_fin});
	}
	public List<Map<String,Object>> crearComprasReportes(String fecha_ini, String fecha_fin) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech_ini = dat.parse(fecha_ini);
		Date fech_fin = dat.parse(fecha_fin);
		String xsql="select c.idcompra, c.nombrecomprador, c.fechacompra, p.nombres, c.total,u.nombres as nombre_usu, u.apellidos as ap_usu from compras c, usuarios u, "+
				 "proveedores p where  c.ciproveedor=p.ciproveedor and fechacompra between ? and ? and c.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech_ini, fech_fin});
	}
	public List<Map<String,Object>> crearProductosReportes(String fecha_ini, String fecha_fin) throws ParseException{
		SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd");
		Date fech_ini = dat.parse(fecha_ini);
		Date fech_fin = dat.parse(fecha_fin);
		String xsql="select p.idproducto,  p.nombre,sum(d.cantidad) as total from productos p, detventa d, ventas v where p.idproducto=d.idproducto and v.idventa=d.idventa  and fechaventa between ? and ? group by p.idproducto ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {fech_ini, fech_fin});
	}
}