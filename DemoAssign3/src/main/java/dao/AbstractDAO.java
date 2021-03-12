package dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionFactory;
import model.CreateOrder;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class AbstractDAO<T> {
	
	  private final Class<?> type;
	  
	  public AbstractDAO() {
		  this.type = (Class<?>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	  }
	  
	  public AbstractDAO(Class<?> type) {
		  this.type=type;
	  }
	
	 public List<T> createObjects(ResultSet resultSet){
		  List<T> list = new ArrayList<T>();
		  
			  try {
				while(resultSet.next()) {
					  @SuppressWarnings("unchecked")
					T instance = (T) type.newInstance();
					  for(Field field: type.getDeclaredFields()) {
						  Object value = resultSet.getObject(field.getName());
						  PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
						  Method method = propertyDescriptor.getWriteMethod();
						  method.invoke(instance,  value);
					  }
					  list.add(instance);
				  }
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  return list;
	  }
	 
	 public  List<T> selectAllObj(){
		  
		  StringBuilder sb = new StringBuilder();
		  sb.append("SELECT * FROM `"+ type.getSimpleName()+"`");
		  
		  String query = sb.toString();
		  Connection connection = ConnectionFactory.getConnection();
		  PreparedStatement statement = null;
		  ResultSet  result = null;
		try {	 
			  statement = connection.prepareStatement(query);
			  result = statement.executeQuery();
			  
			  return createObjects(result);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			ConnectionFactory.close(result);
			ConnectionFactory.close(statement);;
			ConnectionFactory.close((com.mysql.jdbc.Connection) connection);
		}
		  
		  return null;
		
	  }

}
