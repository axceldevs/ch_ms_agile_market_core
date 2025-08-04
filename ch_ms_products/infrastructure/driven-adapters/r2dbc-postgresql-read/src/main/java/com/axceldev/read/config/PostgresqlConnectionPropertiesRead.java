package com.axceldev.read.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostgresqlConnectionPropertiesRead {
    private String host;
    private String hostro;
    private Integer port;
    private String dbname;
    private String username;
    private String password;
}
