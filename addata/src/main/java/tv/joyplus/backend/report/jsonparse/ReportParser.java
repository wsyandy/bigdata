package tv.joyplus.backend.report.jsonparse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tv.joyplus.backend.report.dao.impl.ProcessDaoImpl;
import tv.joyplus.backend.report.dto.ParameterDto;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReportParser {

	private ObjectMapper mapper;
//	private SimpleDateFormat sdf; 
	Log log = LogFactory.getLog(ProcessDaoImpl.class);
	public ReportParser () {
		mapper = new ObjectMapper();
	}
	
	public ParameterDto parseParameter(String param) throws JsonParseException, JsonMappingException, IOException, ParseException {
		log.info(param);
		ParameterDto parameterDto = new ParameterDto();
		Report report = mapper.readValue(param, Report.class);
		parameterDto.setReportId(String.valueOf(report.getReport_id()));
		ReportQuery query = mapper.readValue(report.getQueryJson(), ReportQuery.class);
		parameterDto.setType(query.getType());
		parameterDto.setDataType(query.getDataCycle());
		parameterDto.setFrequency(query.getFrequency());
		if(query.getDataResource()!=null){
			List<String> dataResource = new ArrayList<String>();
			for(String str : query.getDataResource()){
				dataResource.add(str);
			}
			parameterDto.setDataResource(dataResource);
		}
		
		if(query.getDateRange()!=null){
			List<Date> dateRange = new ArrayList<Date>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(String str : query.getDateRange()){
				dateRange.add(sdf.parse(str));
			}
			parameterDto.setDateRange(dateRange.toArray(new Date[2]));
		}
		
		if(query.getItems()!=null){
			List<String> items = new ArrayList<String>();
			for(String str : query.getItems()){
				items.add(str);
			}
			parameterDto.setItems(items);
		}
		
		if(query.getGroupBy()!=null){
			List<String> groupby = new ArrayList<String>();
			for(String str : query.getGroupBy()){
				groupby.add(str);
			}
			parameterDto.setGroupby(groupby);
		}
		log.debug("json string parse success");
		return parameterDto;
	}
}
