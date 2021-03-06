package tv.joyplus.backend.huan.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tv.joyplus.backend.huan.beans.AnalyzerFileInfo;
import tv.joyplus.backend.huan.dao.AnalyzerFileInfoDao;

public class AnalyzerFileInfoImpl extends JdbcDaoSupport implements
		AnalyzerFileInfoDao {

	@Override
	public void save(AnalyzerFileInfo instance) {
		String sql = "INSERT INTO " + AnalyzerFileInfo.TableName() + "(path,filename,offset,status,create_time)"
				+ "VALUES(?,?,?,?,?)";
		getJdbcTemplate().update(sql, new Object[]{instance.getPath(), instance.getFilename(), 0, instance.getStatus(), instance.getCreateTime()});
	}

	@Override
	public List<AnalyzerFileInfo> listAll() {
		String sql = "SELECT * FROM " + AnalyzerFileInfo.TableName();
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<AnalyzerFileInfo>(AnalyzerFileInfo.class));
	}

	@Override
	public List<AnalyzerFileInfo> listUnAnalyzed() {
		String sql = "SELECT * FROM "+AnalyzerFileInfo.TableName()+" WHERE status=0";
		return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<AnalyzerFileInfo>(AnalyzerFileInfo.class));
	}

	@Override
	public void updateStatus(long id, byte status) {
		String sql = "UPDATE "+AnalyzerFileInfo.TableName()+" SET status=? WHERE id=?";
		getJdbcTemplate().update(sql, new Object[]{status, id});
	}

}
