package model.manager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import java.text.DateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;



//@Service indica que la clase es un bean de la capa de negocio
@Service
public class UsuariosManager {
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listarUsuarios(){		
		String xsql="select * from usuarios";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	
	}

	public int acceso_datos(String login, String pass){
		String xsql="select count(*)"+
	                 " from usuarios"+
				     " where login=? and clave=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {login, pass});
	}
	
	public int modificarUsuario(String ci,String nombre,String apellidos){
		String xsql=" update usuarios set nombres=?, apellidos=? where id=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {nombre,apellidos,Integer.parseInt(ci) });
	}
	public int agregarUsuario(int id,String nombre,String apellidos,String login,String pass){
		String xsql="insert into usuarios (id,nombres,apellidos,login,clave) values(?,?,?,?,?) ";
		return this.jdbcTemplate.update( xsql, new Object[] {id,nombre,apellidos,login,pass});
	}
	
	public List<?> verificarCi(String ci){
		String xsql="select * from usuarios where ci=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {Integer.parseInt(ci) });
	}
	public List<?> obtenerNombre(String id){
		String xsql="select * from usuarios where id=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {Integer.parseInt(id) });
	}
	
	public String obtenerLogin(){
		String xsql="select login from usuarios where id=1";
		return this.jdbcTemplate.queryForObject(xsql, new Object[] {},String.class);
	}
	
	public int obtenerEstado(String id){
		String xsql="select estado from usuarios where id=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {Integer.parseInt(id)});
	}
	public int obtenerId(String login, String pass){
		String xsql="select id from usuarios where login=? and clave=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {login, pass});
	}
	public int eliminarUsuario(String ci){
		String xsql="update usuarios set estado=0 where id=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(ci)});
	}
	public int obtenerCi(String login, String pass){
		String xsql="select ciusuario from usuarios where(login=? and pass=?)";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {login,pass});
	}
	
	public int habilitarUsuario(String ci){
		String xsql="update usuarios set estado=1 where id=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(ci)});
	}
	public int modificarpass(String login, String pass){
		String xsql="update usuarios set login=? , clave=? where id=1 ";
		return this.jdbcTemplate.update(xsql, new Object[] {login, pass});
	}
	
	public int modificarpass2(String login, String pass, String id){
		String xsql="update usuarios set login=? , clave=? where id=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {login, pass, Integer.parseInt(id)});
	}
	
	public int comprobarpass(String pass){
		String xsql="select count(*)"+
	                 " from usuarios"+
				     " where clave=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {pass});
	}
	
	public int modificarCaja(String caja){
		String xsql="update caja set monto=? where idmonto=1 ";
		return this.jdbcTemplate.update(xsql, new Object[] {Double.parseDouble(caja)});
	}
	
	public int ultimoId(){
		String xsql="select max(id) from usuarios";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {});
	}

	
}