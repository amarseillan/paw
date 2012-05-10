package zonaProp.transfer.bussiness;

import java.util.List;

import zonaProp.services.PhotoService;
import zonaProp.services.UserService;

public class Publication {

	private int publicationId;
	private int userId;
	private String address;
	private String city;
	private double price;
	private int environments;
	private double covered;
	private double uncovered;
	private int age;
	private String description;
	private boolean active;

	List<PropertyServices> propertyServices;

	private PropertyType propertyType;
	private OperationType operationType;

	private User publisher = null;

	private List<Photo> photos = null;

	public Publication(int publicationId, int userId,
			PropertyType propertyType, OperationType operationType,
			String address, String city, double price, int environments,
			double covered, double uncovered, int age,
			List<PropertyServices> propertyServices, String description,
			boolean active) {
		super();
		this.publicationId = publicationId;
		this.userId = userId;
		this.address = address;
		this.city = city;
		this.price = price;
		this.environments = environments;
		this.covered = covered;
		this.uncovered = uncovered;
		this.age = age;

		this.propertyServices = propertyServices;

		this.description = description;
		this.active = active;

		this.propertyType = propertyType;
		this.operationType = operationType;
	}

	public List<PropertyServices> getPropertyServices() {
		return propertyServices;
	}

	public int getPublicationId() {
		return publicationId;
	}

	public int getUserId() {
		return userId;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public double getPrice() {
		return price;
	}

	public int getEnvironments() {
		return environments;
	}

	public double getCovered() {
		return covered;
	}

	public double getUncovered() {
		return uncovered;
	}

	public int getAge() {
		return age;
	}

	public String getDescription() {
		return description;
	}

	public User getPublisher() {
		if (publisher == null) {
			UserService us = UserService.getInstance();
			publisher = us.getUser(userId);
		}

		return publisher;
	}

	public List<Photo> getPhotos() {
		if (photos == null) {
			PhotoService ps = PhotoService.getInstance();
			photos = ps.getPhotosByPublication(this);
		}

		return photos;
	}

	public boolean isActive() {
		return active;
	}

}
