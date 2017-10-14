environments {
    production {
        dataSource {
            dbCreate = "update"
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQLDialect
            uri = new URI(System.env.DATABASE_URL?:"postgres://oukebtsqlbanep:f10bccbcc0f806e2b105f96532e9d617e7f6197129f1b4e0c459e8c4875be8b4@ec2-50-19-218-160.compute-1.amazonaws.com:5432/d2bo4c025aamd1")
            url = "jdbc:postgresql://" + uri.host + ":" + uri.port + uri.path
            username = uri.userInfo.split(":")[0]
            password = uri.userInfo.split(":")[1]
        }
    }
}