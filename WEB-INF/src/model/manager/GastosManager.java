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
public class GastosManager {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Map<String,Object>> listar_gastos(){
		String xsql="select * from gastos";
		return this.jdbcTemplate.queryForList(xsql, new Object[] { });
	}
	public int agregar_gasto(String fecha,String id,String login,String descripcion, double monto, String hora) throws ParseException  {
		SimpleDateFormat dat = new SimpleDateFormat("yyy-MM-dd");
			Date fechagasto = dat.parse(fecha);
		
		String xsql="insert into gastos(fechagasto,id_usuario,loginusu,descripcion,montogasto,horagasto) values(?,?,?,?,?,?)";
		return this.jdbcTemplate.update(xsql, new Object[] {fechagasto,Integer.parseInt(id),login,descripcion,monto,hora});
	}
	public double restarmonto(double monto,double caja){
		Double montofinal=caja - monto;
		String xsql="update caja set monto=? where idmonto=1  ";
		return (double) this.jdbcTemplate.update( xsql, new Object[] {montofinal});
	}
	public double sumarmonto(double monto,double caja){
		Double montofinal=caja + monto;
		String xsql="update caja set monto=? where idmonto=1  ";
		return (double) this.jdbcTemplate.update( xsql, new Object[] {montofinal});
	}
	public double valorCaja(){
		String xsql="select monto from caja where idmonto=1 ";
		return (double) this.jdbcTemplate.queryForInt( xsql, new Object[] {});
	}
	public int modificarGasto(int idgasto,String descripcion,double monto){
		String xsql=" update gastos set descripcion=?, montogasto=? where idgasto=? ";
		return this.jdbcTemplate.update(xsql, new Object[] {descripcion,monto,idgasto });
	}
	

	



	
}