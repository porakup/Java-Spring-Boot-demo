package dev.demo.registration.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;
	
	@Override
	public boolean checkDuplicateEmail(String email) {
		
		StringBuilder sql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sql.append(" SELECT COUNT(U.EMAIL) FROM USER U WHERE U.EMAIL = :EMAIL ");
		params.put("EMAIL", email);
		
		Integer result = namedJdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
		
		return result > 0 ? true : false;
		
	}
	
	@Override
	public boolean checkDuplicateTelephoneNumber(String telephoneNumber) {
		
		StringBuilder sql = new StringBuilder();
		Map<String, Object> params = new HashMap<String, Object>();
		
		sql.append(" SELECT COUNT(U.TELEPHONE_NUMBER) FROM USER U WHERE U.TELEPHONE_NUMBER = :TELEPHONE_NUMBER ");
		params.put("TELEPHONE_NUMBER", telephoneNumber);
		
		Integer result = namedJdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
		
		return result > 0 ? true : false;
		
	}

}
