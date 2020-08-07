package kmsg.sms.mst.adapter;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kmsg.sms.common.SvcStatus;
import kmsg.sms.mst.daoimpl.QuotesDaoImpl;
import kmsg.sms.mst.model.QuoteSupplier;
import kmsg.sms.mst.model.QuoteSupplierItem;
import kmsg.sms.mst.model.QuotesItem;
import kmsg.sms.mst.model.QuotesModel;

@Component
public class QuotesAdapter 
{
	@Autowired
	QuotesDaoImpl dao;
	
	public void setSchoolId(int schoolId)
	{
		dao.setSchoolId(schoolId);
	}

	public Map<String, Object> saveQuote(String quote) 
	{
		QuotesModel model = new QuotesModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(quote, QuotesModel.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving quote.");
		}
		if(model.getQuoteId() == 0)
			return dao.saveQuote(model);
		else
			return dao.updateQuote(model);
	}

	public Map<String, Object> listQuote() 
	{
		return dao.selectQuote();
	}

	public Map<String, Object> listQuoteItems(String quoteId) 
	{
		return dao.selectQuoteItems(Integer.parseInt(quoteId));
	}

	public Map<String, Object> saveQuoteItem(String quoteItem) 
	{
		QuotesItem model = new QuotesItem();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(quoteItem, QuotesItem.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving quote item");
		}
		if(model.getQuoteItemId() == 0)
			return dao.saveQuoteItem(model);
		else
			return dao.updateQuoteItem(model);
	}

	public Map<String, Object> listQuoteSupplier(String quoteId) 
	{
		return dao.selectQuoteSupplier(Integer.parseInt(quoteId));
	}

	public Map<String, Object> saveQuoteSupplier(String quoteSupplier) 
	{
		QuoteSupplier model = new QuoteSupplier();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(quoteSupplier, QuoteSupplier.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving quote supplier");
		}
		if(model.getQuoteSupplierId() == 0)
			return dao.saveQuoteSupplier(model);
		else
			return dao.updateQuoteSupplier(model);
	}

	public Map<String, Object> listQuoteSupplierItemList(String quoteSupplierId) 
	{
		return dao.selectQuoteSupplierItem(Integer.parseInt(quoteSupplierId));
	}

	public Map<String, Object> saveQuoteSupplierItem(String quoteSupplierItem)
	{
		QuoteSupplierItem model = new QuoteSupplierItem();
		ObjectMapper mapper = new ObjectMapper();
		try {
			model = mapper.readValue(quoteSupplierItem, QuoteSupplierItem.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return SvcStatus.GET_FAILURE("Exception occurred in saving quote supplier item");
		}
		if(model.getQuoteSupplierItemId() == 0)
			return dao.saveQuoteSupplierItem(model);
		else
			return dao.updateQuoteSupplierItem(model);
	}
}
