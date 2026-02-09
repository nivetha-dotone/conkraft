package com.wfd.dot1.cwfm.dto;


public class CardDto {
    private String title;
    private String description;
    private String count;
    private String borderClass;
    private String iconClass;
    private String iconUrl;
    private String link;

    private String heading;
    public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public CardDto() {
        // Default constructor
    }

    public CardDto(String title, String description, String count,
                   String borderClass, String iconClass, String iconUrl, String link) {
        this.title = title;
        this.description = description;
        this.count = count;
        this.borderClass = borderClass;
        this.iconClass = iconClass;
        this.iconUrl = iconUrl;
        this.link = link;
    }

    public CardDto(String title, String description, String count, String borderClass, String iconClass, String iconUrl,
			String link, String heading) {
		super();
		this.title = title;
		this.description = description;
		this.count = count;
		this.borderClass = borderClass;
		this.iconClass = iconClass;
		this.iconUrl = iconUrl;
		this.link = link;
		this.heading = heading;
	}

	// Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBorderClass() {
        return borderClass;
    }

    public void setBorderClass(String borderClass) {
        this.borderClass = borderClass;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

