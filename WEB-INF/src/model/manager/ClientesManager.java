package model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

//@Service indica que la clase es un bean de la capa de negocio
@Service
public class ClientesManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List <Map<String,Object>> listar_clientes(){
		String xsql="select * from clientes";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {});
	}	
		

	public int agregar_cliente(String nombre){
		String xsql="insert into clientes (nombres) values (?) returning codcliente";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {nombre});
	}
	
	public int adicionar_cliente(String nombres,int ci, int celular){
		String xsql="insert into clientes(nombres,ci,celular) values(?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {nombres,ci,celular});
	}
	public int obtenerCod(String cliente){
		String xsql="select codcliente from clientes where nombres=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {cliente});
	}
	
	public int modificar_cliente(int cod,String nombres){
		String xsql="update clientes set nombres=? where codcliente=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {nombres,cod});
	}
	
	public int eliminar_cliente(String ci){
		String xsql="delete from clientes where cicliente=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {ci});
	}
	
	public int verificarCliente(String cliente){
		String xsql="select count(*)"+
                " from clientes"+
			     " where nombres=?";;
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {cliente});
	}
}