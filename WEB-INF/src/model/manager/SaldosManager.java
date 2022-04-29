package model.manager;



import java.sql.Time;
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
public class SaldosManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listar_saldos(){
		String xsql="select  v.idventa, v.fechaventa, v.horaventa, v.total,v.totalcredito, c.nombres  from ventas v, clientes c where v.codcliente=c.codcliente and estado=0";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	public int modificarsaldo(int idventa,double monto){
		String xsql=" update ventas set total=? where idventa=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {monto,idventa });
	}
	
	public int modificarmontocredito(int idventa,double total, double totalcredito){
		String xsql=" update ventas set total=?, totalcredito=? where idventa=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {total,totalcredito,idventa });
	}
	
	public int valortotalcredito(int idventa){
		String xsql="select totalcredito from ventas where idventa=? ";
		return  this.jdbcTemplate.queryForInt( xsql, new Object[] {idventa});
	}
	
	public int valortotal(int idventa){
		String xsql="select total from ventas where idventa=? ";
		return  this.jdbcTemplate.queryForInt( xsql, new Object[] {idventa});
	}
	
	public int modificarCaja(double monto){
		String xsql=" update caja set monto=? where idmonto='1' ";
		return this.jdbcTemplate.update(xsql, new Object[] {monto});
	}
	
	public List<Map<String,Object>> llenarCaja(){
		String xsql="select monto from caja where idmonto=1";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}

	



	
}