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
public class ProveedorManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public List<Map<String,Object>> listar_provee(){
		String xsql="select * from proveedores ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {});
	}
	public List<Map<String,Object>> listarpro(){		
		String xsql="select * from proveedores";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	
	}
	public int adicionar_proveedor(String cipro,String nombres, int celular){
		String xsql="insert into proveedores(ciproveedor,nombres,celular) values(?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {cipro,nombres,celular});
	}
	public int modificarProveedor(String ci,String nombres, int celular, int id){
		String xsql=" update proveedores set ciproveedor=?, nombres=?, celular=? where idproveedor=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {ci,nombres,celular,id});
	}
}