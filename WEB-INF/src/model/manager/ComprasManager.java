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
public class ComprasManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<Map<String,Object>> listar(){
		String xsql="select * from proveedores ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {});
	}
	public List<Map<String,Object>> listarcompras(){
		String xsql="select c.idcompra, c.nombrecomprador, c.fechacompra, c.horacompra, p.nombres, c.total,u.nombres as nombre_usu, u.apellidos as ap_usu from compras c,usuarios u, "+
				 "proveedores p where  c.ciproveedor=p.ciproveedor and c.id_usuario=u.id";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {});
	}
	public List<Map<String,Object>> listarcompraEspecifica(String cod){
		String xsql="select c.*, p.nombres, u.nombres as nombre_usu, u.apellidos as ap_usu from compras c,usuarios u, "+
				 "proveedores p where  c.ciproveedor=p.ciproveedor and c.id_usuario=u.id and c.idcompra=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {Integer.parseInt(cod)});
	}
	public List<Map<String,Object>> listardetalle(String cod){
		String xsql="select c.idcompra,c.fechacompra,d.total, p.nombre, d.cantidad, d.precio from compras c, productos p, detcompra d where c.idcompra=d.idcompra and p.idproducto=d.idproducto and c.idcompra=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {Integer.parseInt(cod)});
	}
	
	public int agregar_compra(String ciproveedor,String id,String nombrec, String fecha,int factura,double total,int estado) throws ParseException  {
		SimpleDateFormat dat = new SimpleDateFormat("dd-MM-yyyy");
		Date fech = dat.parse(fecha);
		String xsql="insert into compras(ciproveedor,id_usuario,nombrecomprador,fechacompra, factura,total,estado) values(?,?,?,?,?,?,?) returning idcompra";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {ciproveedor,Integer.parseInt(id),nombrec,fech,factura,total,estado});
	}
	
	public int ultimaCompra()  {
		String xsql="select max(idcompra)from compras";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {});
	}
	public int detalle_compra(int producto,int idcompra,double cantidad, double precio)  {
		String xsql="insert into detcompra(idproducto,idcompra,cantidad,precio,total) values(?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {producto,idcompra,cantidad,precio,(cantidad*precio)});
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