package com.company.server.runtime.enums;

public enum LastError {
    NULL {
        final String defaultText = "";
        String currentText = "";

        @Override
        public String getErrorText() {
            return currentText;
        }

        @Override
        public void setAdditionalText(String additionalText) {
            currentText = defaultText + additionalText;
        }
    },
    ERROR_COLLISION_1 {
        final String defaultText = "Невозможно обработать столкновение пары по координате X = ";
        String currentText = "";

        @Override
        public String getErrorText() {
            return currentText;
        }

        @Override
        public void setAdditionalText(String additionalText) {
            currentText = defaultText + additionalText;
        }
    };

    public abstract String getErrorText();
    public abstract void setAdditionalText(String additionalText);
}
