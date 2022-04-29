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
public class ventasManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listarventas(){
		String xsql="select v.idventa, v.fechaventa, v.horaventa, v.total, c.nombres,u.nombres as nombre_usu, u.apellidos as ap_usu from ventas v, usuarios u, "+
				 "clientes c where v.codcliente=c.codcliente and v.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	
	public List<Map<String,Object>> listarventa(int cod){
		String xsql="select v.*, u.nombre ,u.apellidos, c.nombre as nombrecli, c.apellidos as apellidoscli from ventas v, usuarios u, clientes c where(u.ciusuario=v.ciusuario and c.cicliente=v.cicliente and v.codventa=?)";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {cod });
	}
	
	public int agregar_venta(String fecha,String id_usuario,int cliente,String hora,double tottal,int estado, double totalcredito) throws ParseException  {
		SimpleDateFormat dat = new SimpleDateFormat("yyy-MM-dd");
			Date fecha_venta = dat.parse(fecha);
		String xsql="insert into ventas(fechaventa,id_usuario,codcliente,horaventa,total,estado,totalcredito) values(?,?,?,?,?,?,?) returning idventa";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {fecha_venta,Integer.parseInt(id_usuario),cliente,hora,tottal,estado,totalcredito});
	}
	
	public int ultimaVenta()  {
		String xsql="select max(idventa)from ventas";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {});
	}
	public int detalle_venta(int idventa,int producto,double cantidad, double precio)  {
		String xsql="insert into detventa(idventa,idproducto,cantidad,precio,total) values(?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {idventa,producto,cantidad,precio,(cantidad*precio)});
	}
	public List<Map<String,Object>> listarventaEspecifica(String cod){
		String xsql="select v.idventa, v.fechaventa, v.horaventa, v.total, c.nombres,u.nombres as nombre_usu, u.apellidos as ap_usu from ventas v, usuarios u, "+
				 "clientes c where v.codcliente=c.codcliente and v.id_usuario=u.id and v.idventa=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {Integer.parseInt(cod) });
	}
	public List<Map<String,Object>> obtenerCliente(int cod){
		String xsql="select c.* from ventas v,clientes c where v.codventa=? and v.cicliente=c.cicliente ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {cod });
	}
	
	public List<?> obtenerdetalle(int cod){
		String xsql="select v.*, p.nombre, d.cantidad, d.precio, d.total from ventas v, detventa d, productos p where v.idventa=d.idventa and d.idproducto=p.idproducto and v.idventa=? ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {cod});
	}
	public String recuperar_caja(){
		String xsql="select monto from caja where idmonto='1'  ";
		return this.jdbcTemplate.queryForObject(xsql, new Object[] {}, String.class);
	}
	public int modificar_monto(double monto){
		String xsql=" update caja set monto=? where idmonto='1'  ";
		return this.jdbcTemplate.update(xsql, new Object[] {monto});
	}
	

	



	
}