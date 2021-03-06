package zonaProp.web.command;

import zonaProp.model.publication.OperationType;
import zonaProp.model.publication.PropertyType;
import zonaProp.model.publication.Search;
import zonaProp.model.user.User;

public class SearchForm {

	String max = null;
	String min = null;
	OperationType operationType = null;
	PropertyType propertyType = null;
	User publisher = null;
	boolean ascending = true;
	int page = 0;
	String pageSize = null;

	public SearchForm() {
	}

	public SearchForm(String max, String min, OperationType operationType,
			PropertyType propertyType, User publisher, boolean ascending,
			int page, String pageSize) {
		super();
		this.max = max;
		this.min = min;
		this.operationType = operationType;
		this.propertyType = propertyType;
		this.publisher = publisher;
		this.ascending = ascending;
		this.page = page;
		this.pageSize = pageSize;
	}

	public User getPublisher() {
		return publisher;
	}

	public void setPublisher(User publisher) {
		this.publisher = publisher;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		if (max != null)
			this.max = max.trim();
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		if (min != null)
			this.min = min.trim();
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public Search build() {
		return new Search(min == null || min.isEmpty() ? null : Double
				.parseDouble(min), max == null || max.isEmpty() ? null : Double
				.parseDouble(max), operationType, propertyType, ascending,
				publisher, page, pageSize == null || pageSize.isEmpty() ? 30
						: Integer.parseInt(pageSize));
	}
}
