/*
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.    
 */
package org.apache.wiki.providers;

import org.apache.wiki.WikiPage;
import org.apache.wiki.api.core.Engine;
import org.apache.wiki.api.exceptions.ProviderException;
import org.apache.wiki.search.QueryItem;
import org.apache.wiki.search.SearchResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 *  A provider who counts the hits to different parts.
 */
public class CounterProvider implements WikiPageProvider {

    public int m_getPageCalls     = 0;
    public int m_pageExistsCalls  = 0;
    public int m_getPageTextCalls = 0;
    public int m_getAllPagesCalls = 0;
    public int m_initCalls        = 0;

    WikiPage[]    m_pages         = new WikiPage[0];
    
    String m_defaultText = "[Foo], [Bar], [Blat], [Blah]";


    @Override
    public void initialize( final Engine engine, final Properties props ) {
        m_pages = new WikiPage[]
                  { new WikiPage(engine, "Foo"),
                    new WikiPage(engine, "Bar"),
                    new WikiPage(engine, "Blat"),
                    new WikiPage(engine, "Blaa") };
        
        m_initCalls++;
        
        for( int i = 0; i < m_pages.length; i++ ) 
        {
            m_pages[i].setAuthor("Unknown");
            m_pages[i].setLastModified( new Date(0L) );
            m_pages[i].setVersion(1);
        }
    }

    @Override
    public String getProviderInfo()
    {
        return "Very Simple Provider.";
    }

    @Override
    public void putPageText( final WikiPage page, final String text )
        throws ProviderException
    {
    }

    @Override
    public boolean pageExists( final String page )
    {
        m_pageExistsCalls++;

        return findPage( page ) != null;
    }

    @Override
    public boolean pageExists( final String page, final int version )
    {
        return pageExists (page);
    }

    @Override
    public Collection< SearchResult > findPages( final QueryItem[] query )
    {
        return null;
    }

    private WikiPage findPage( final String page )
    {
        for( int i = 0; i < m_pages.length; i++ )
        {
            if( m_pages[i].getName().equals(page) )
                return m_pages[i];
        }

        return null;
    }

    @Override
    public WikiPage getPageInfo( final String page, final int version )
    {            
        m_getPageCalls++;

        final WikiPage p = findPage(page);

        return p;
    }

    @Override
    public Collection< WikiPage > getAllPages()
    {
        m_getAllPagesCalls++;

        final List<WikiPage> l = new ArrayList<>();

        for( int i = 0; i < m_pages.length; i++ )
        {
            l.add( m_pages[i] );
        }

        return l;
    }

    @Override
    public Collection< WikiPage > getAllChangedSince( final Date date )
    {
        return new ArrayList<>();
    }

    @Override
    public int getPageCount()
    {
        return m_pages.length;
    }

    @Override
    public List< WikiPage > getVersionHistory( final String page )
    {
        return new Vector<>();
    }

    @Override
    public String getPageText( final String page, final int version )
    {
        m_getPageTextCalls++;
        return m_defaultText;
    }

    @Override
    public void deleteVersion( final String page, final int version )
    {
    }

    @Override
    public void deletePage( final String page )
    {
    }

    /* (non-Javadoc)
     * @see org.apache.wiki.providers.WikiPageProvider#movePage(java.lang.String, java.lang.String)
     */
    @Override
    public void movePage( final String from, final String to ) throws ProviderException
    {
    }
    
    
}
