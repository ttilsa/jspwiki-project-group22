package org.apache.wiki.auth;

import java.util.Properties;

import org.apache.wiki.TestEngine;
import org.apache.wiki.api.core.Session;
import org.apache.wiki.api.spi.Wiki;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.sourceforge.stripes.mock.MockHttpServletRequest;

class DefaultAuthenticationManagerTest {
	
	private AuthenticationManager m_auth;
	private TestEngine m_engine;

	@BeforeEach
    public void setUp() throws Exception {
        final Properties props = TestEngine.getTestProperties();
        m_engine = new TestEngine( props );
        m_auth = m_engine.getManager( AuthenticationManager.class );
    }

	/**
     * 
     * dev-sp1 
     * Tests login is failed when cookies are disabled by user.
     * This tests with servlet request
     */
    @Test
    public void testLoginWithoutCookies() throws Exception {
    	final MockHttpServletRequest request;
        final Session wikiSession;
        
        request = m_engine.newHttpRequest();
        
        // create new session from the request
        wikiSession = Wiki.session().find( m_engine, request );
        
        // user disabled cookies
        wikiSession.setCookiesEnabled(false);
    	
        boolean isLoginSucceed = m_auth.login( request );
        Assertions.assertFalse( isLoginSucceed );
    }
    
    /**
     * 
     * dev-sp1 
     * Tests login is succeeded when cookies are enabled by user.
     * This tests with servlet request
     */
    @Test
    public void testLoginWithCookies() throws Exception {
    	final MockHttpServletRequest request;
        final Session wikiSession;
        
        request = m_engine.newHttpRequest();
        
        // create new session from the request
        wikiSession = Wiki.session().find( m_engine, request );
        
        // user enabled cookies
        wikiSession.setCookiesEnabled(true);
    	
        boolean isLoginSucceed = m_auth.login( request );
        Assertions.assertTrue( isLoginSucceed );
    }
}
