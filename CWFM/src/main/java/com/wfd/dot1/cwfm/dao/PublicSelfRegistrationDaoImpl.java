package com.wfd.dot1.cwfm.dao;

import com.wfd.dot1.cwfm.dao.PublicSelfRegistrationDao;
import com.wfd.dot1.cwfm.util.QueryFileWatcher;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class PublicSelfRegistrationDaoImpl implements PublicSelfRegistrationDao {

	private final JdbcTemplate jdbcTemplate;

	public PublicSelfRegistrationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String insertRegistration() {
		return QueryFileWatcher.getQuery("INSERT_REGISTRATION");
	}

	@Override
	public long insertRegistration(long linkId, String aadhaar, String firstName, String lastName, String mobileNumber,
			String registrationData) {
		String sql = insertRegistration();
//        String sql = "INSERT INTO dbo.WORKMAN_SELF_REGISTRATION " +
//                "(LinkId, AadharNumber, FirstName, LastName, MobileNumber, RegistrationData, Status) " +
//                "VALUES (?, ?, ?, ?, ?, ?, 'SUBMITTED')";

		KeyHolder holder = new GeneratedKeyHolder();

		jdbcTemplate.update((PreparedStatementCreator) con -> {

			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setLong(1, linkId);
			ps.setString(2, aadhaar);
			ps.setString(3, firstName);
			ps.setString(4, lastName);
			ps.setString(5, mobileNumber);
			ps.setString(6, registrationData);

			return ps;

		}, holder);

		if (holder.getKey() == null) {
			throw new IllegalStateException("Registration ID was not generated");
		}

		return holder.getKey().longValue();
	}

	public String insertRegistrationDocument() {
		return QueryFileWatcher.getQuery("INSERT_REGISTRATION_DOCUMENT");
	}

	@Override
	public void insertRegistrationDocument(long registrationId, String documentType, String storedFileName,
			String storagePath) {
		String sql = insertRegistrationDocument();
//		String sql = "INSERT INTO dbo.WORKMAN_SELF_REG_DOCUMENT "
//				+ "(RegistrationId, DocumentType, StoredFileName, StoragePath) " + "VALUES (?, ?, ?, ?)";

		jdbcTemplate.update(sql, registrationId, documentType, storedFileName, storagePath);
	}
}