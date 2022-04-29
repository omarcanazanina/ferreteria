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
public class inventarioManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listar_inventario(){
		String xsql="select p.idproducto,p.nombre,t.punit, t.cantidadtienda,t.montotienda  from inventariotienda t, productos p where p.idproducto=t.idproducto ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	public List<Map<String,Object>> listar_inventario2(){
		String xsql="select p.idproducto,p.nombre,c.punit, c.cantidadcasa,c.montocasa  from inventariocasa c, productos p where p.idproducto=c.idproducto ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	public int agregarRol(String id, String nombre){
		String xsql="insert into roles (id,nombre_rol) values(?,?) ";
		return this.jdbcTemplate.update( xsql, new Object[] {id,nombre});
	}
	
	

	



	
}