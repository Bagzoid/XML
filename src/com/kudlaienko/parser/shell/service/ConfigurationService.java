package com.kudlaienko.parser.shell.service;

import java.util.List;

public class ConfigurationService {
    private ParserService parserService;
    private ValidationService validationService;

    public static class Builder {
        private ParserService parserService;
        private ValidationService validationService;

        public Builder() {
            parserService = new ParserService();
            validationService = new ValidationService();
        }

        public <P extends Parser> Builder addParser(Class<P> classType, P parser) {
            parserService.addService(classType, parser);
            return this;
        }

        public Builder addParser(List<Parser> parserList) {
            parserService.addService(parserList);
            return this;
        }

        public Builder setValidationService(ValidationService validationService) {
            this.validationService = validationService;
            return this;
        }

        public ConfigurationService build() {
            ConfigurationService configurationService = new ConfigurationService();
            configurationService.setParserService(parserService);
            configurationService.setValidationService(validationService);
            return configurationService;
        }
    }

    public static ConfigurationService.Builder getBuilder() {
        return new ConfigurationService.Builder();
    }

    private ConfigurationService() {
    }

    public ParserService getParserService() {
        return parserService;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    public void setValidationService(ValidationService validationService) {
        this.validationService = validationService;
    }
}
