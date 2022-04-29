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
public class productosManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List <Map<String,Object>> listar_productos(){
		String xsql="select p.nombre,p.inventario1,c.idproducto,c.punit,c.pref,c.cantidadcasa, t.cantidadtienda  from inventariocasa c, inventariotienda t, productos p where c.idproducto=t.idproducto and p.idproducto=t.idproducto";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {});
	}	
		

	public int adicionar_pro(int codproducto,String nombre,double punit,double cantidad,double monto,String inventario,double pref){
		String xsql="insert into productos(idproducto,nombre,punit,cantidad,monto,inventario1,pref) values(?,?,?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {codproducto,nombre,punit,cantidad,monto,inventario,pref});
	}

	public int adicionar_producto(int codproducto,double punit,double cantidad, double monto,double pref){
		String xsql="insert into inventariotienda(idproducto,punit,cantidadtienda,montotienda,pref) values(?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {codproducto,punit,cantidad,monto,pref});
	}
	public int adicionar_producto2(int codproducto,double punit,double cantidad,double monto,double pref){
		String xsql="insert into inventariocasa(idproducto,punit,cantidadcasa,montocasa,pref) values(?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {codproducto,punit,cantidad,monto,pref});
	}
	public int adicionar_productoinv(int codproducto,double punit,double cantidad, double monto,double pref){
		String xsql="insert into inventariocasa(idproducto,punit,cantidadcasa,montocasa,pref) values(?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {codproducto,punit,cantidad,monto,pref});
	}
	public int adicionar_producto3(int codproducto,double punit,double cantidad,double monto,double pref){
		String xsql="insert into inventariotienda(idproducto,punit,cantidadtienda,montotienda,pref) values(?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {codproducto,punit,cantidad,monto,pref});
	}
	
	public int modificar_productos(int cod,String nombre){
		String xsql=" update productos set nombre=?  where idproducto=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {nombre,cod });
	}
	public int modificar_productostienda(int cod,double punit,double cantidad,double monto,double pref){
		String xsql=" update inventariotienda set punit=?,cantidadtienda=?,montotienda=?,pref=? where idproducto=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {punit,cantidad,monto,pref,cod });
	}
	public int modificar_productoscasa1(int cod,double punit){
		String xsql=" update inventariocasa set punit=? where idproducto=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {punit,cod });
	}
	public int modificar_productoscasa(int cod,double punit,double cantidad,double monto,double pref){
		String xsql=" update inventariocasa set punit=?,cantidadcasa=?,montocasa=?,pref=? where idproducto=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {punit,cantidad,monto,pref,cod });
	}
	public int modificar_productostienda1(int cod,double punit){
		String xsql=" update inventariocasa set punit=? where idproducto=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {punit,cod });
	}
	public int eliminar_producto(String xcodproducto){
		String xsql=" update productos set estado=0 where codproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodproducto)});
	}
	
	public int habilitar_productos(String xcodproducto){
		String xsql=" update productos set estado=1 where codproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {Integer.parseInt(xcodproducto)});
	}
	
	//modificado
	//obtener prod con pref
	public List<?> obtenerProducto(int cod){
		String xsql="select p.idproducto,p.nombre,t.punit,t.pref from productos p,inventariotienda t where p.idproducto=t.idproducto and t.idproducto=? ";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {cod });
		
	}public List<?> obtenerProducto2(int cod){
		String xsql="select * from productos  where idproducto=?";
		return this.jdbcTemplate.queryForList(xsql, new Object[] {cod });
	}
	//modificado
	public String obtenerStock(int cod){
		String xsql="select cantidadtienda from inventariotienda where idproducto=?  ";
		return this.jdbcTemplate.queryForObject(xsql, new Object[] {cod}, String.class);
	}
	//modificado
	public int actualizarStock(int cod, double total,double monto){
		String xsql=" update inventariotienda set cantidadtienda=? ,montotienda=? where idproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {total,monto,cod});
	}
	public int actualizarStock2(int cod, double total){
		String xsql=" update productos set cantidad=?  where idproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {total,cod});
	}
	//modificado
	public String sacartotal(int cod){
		String xsql="select montotienda from inventariotienda where idproducto=?  ";
		return this.jdbcTemplate.queryForObject(xsql, new Object[] {cod}, String.class);
	}
	
	public int verificarProducto( String producto){
		String xsql="select count(*)"+
	                 " from productos"+
				     " where idproducto=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {Integer.parseInt(producto)});
	}
	//modificado
	public int verificarCantidad( String producto){
		String xsql="select cantidadtienda"+
					" from inventariotienda"+
			    	" where idproducto=?";
		return this.jdbcTemplate.queryForInt(xsql, new Object[] {Integer.parseInt(producto)});
	}
	public int descontar_inventario(int cod,double stock,double monto){
		String xsql=" update inventariotienda set cantidadtienda=? ,montotienda=? where idproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {stock,monto,cod});
	}
	public int modificar_inventariocasa(int cod,double stock,double monto){
		String xsql=" update inventariocasa set cantidadcasa=? ,montocasa=? where idproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {stock,monto,cod});
	}
	public int modificar_inventariotienda(int cod,double stock,double monto){
		String xsql=" update inventariotienda set cantidadtienda=? ,montotienda=? where idproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {stock,monto,cod});
	}
	
	public String recuperarstockCasa(int cod){
		String xsql="select cantidadcasa from inventariocasa where idproducto=?  ";
		return this.jdbcTemplate.queryForObject(xsql, new Object[] {cod}, String.class);
	}
	public String recuperarstockTienda(int cod){
		String xsql="select cantidadtienda from inventariotienda where idproducto=?  ";
		return this.jdbcTemplate.queryForObject(xsql, new Object[] {cod}, String.class);
	}
	public int actualizarstock_casa(int cod,double stock,double monto){
		String xsql=" update inventariocasa set cantidadcasa=? ,montocasa=? where idproducto=?  ";
		return this.jdbcTemplate.update(xsql, new Object[] {stock,monto,cod});
	}

}