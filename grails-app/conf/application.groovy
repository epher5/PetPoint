

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.lai.petpoint.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.lai.petpoint.UserRole'
grails.plugin.springsecurity.authority.className = 'com.lai.petpoint.Role'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['permitAll']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['permitAll']],
	[pattern: '/index.gsp',      access: ['permitAll']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	[pattern: '/dbconsole/**',   access: ['permitAll']],
	[pattern: '/home/**',  access: ['permitAll']], // for now
	[pattern: '/abuserGeo/**',  access: ['permitAll']], // for now
	[pattern: '/bewareGeo/**',  access: ['permitAll']], // for now
	[pattern: '/**/**',          access: ['isFullyAuthenticated()']], // for now
]
//grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
//grails.plugin.springsecurity.interceptUrlMap = [
//		[pattern: '/**/**',      access: ['isFullyAuthenticated()']], // for now
//]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

grails.plugin.springsecurity.roleHierarchy = '''
    ROLE_ADMIN > ROLE_USER
'''
