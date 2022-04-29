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
public class SaldosComprasManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listar_saldoscompras(){
		String xsql="select  v.idcompra, v.fechacompra, v.total, c.nombres  from compras v, proveedores c where v.ciproveedor=c.ciproveedor and estado=0";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	public int modificarsaldo(int idventa,double monto){
		String xsql=" update compras set total=? where idcompra=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {monto,idventa });
	}
	
	public int modificarCaja(double monto){
		String xsql=" update caja set monto=? where idmonto='1' ";
		return this.jdbcTemplate.update(xsql, new Object[] {monto});
	}
	
	public List<Map<String,Object>> llenarCaja1(){
		String xsql="select monto from caja where idmonto=1";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}

	



	
}