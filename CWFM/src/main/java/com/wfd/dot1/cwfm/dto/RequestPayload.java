package com.wfd.dot1.cwfm.dto;

public class RequestPayload {
    private Data data;

    // Getter & Setter
    public Data getData() {
        return data;
    }
    public void setData(Data data) {
        this.data = data;
    }

    // Inner class for "data"
    public static class Data {
        private boolean signup_flow;
        private String logo_url;
        private boolean skip_main_screen;
        private int expiry_minutes;
        public int getExpiry_minutes() {
			return expiry_minutes;
		}
		public void setExpiry_minutes(int expiry_minutes) {
			this.expiry_minutes = expiry_minutes;
		}
		// Getters & Setters
        public boolean isSignup_flow() {
            return signup_flow;
        }
        public void setSignup_flow(boolean signup_flow) {
            this.signup_flow = signup_flow;
        }

        public String getLogo_url() {
            return logo_url;
        }
        public void setLogo_url(String logo_url) {
            this.logo_url = logo_url;
        }

        public boolean isSkip_main_screen() {
            return skip_main_screen;
        }
        public void setSkip_main_screen(boolean skip_main_screen) {
            this.skip_main_screen = skip_main_screen;
        }
    }
}
