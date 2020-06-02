package com.jxl.lucene;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.jxl.entity.Blog;
import com.jxl.util.DateUtil;
import com.jxl.util.StringUtil;
import com.jxl.util.WebFileUtil;

/**
 * title:BlogIndex.java
 * description:����������
 * time:2019��2��9�� ����11:14:59
 * author:jxl
 */
public class BlogIndex {

	private Directory dir=null;
	
	//private final String indexStorePath="C://lucene";
	//lucene�����ļ� ����rootPath+indexFile��
	private final String indexFile="luceneIndex";
	
	private HttpServletRequest request;
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public BlogIndex() {
		super();
	}

	/**
	 * title:BlogIndex.java
	 * description:��ȡIndexWriterʵ��
	 * time:2019��2��9�� ����11:15:08
	 * author:jxl
	 * @return
	 * @throws Exception
	 */
	private IndexWriter getWriter()throws Exception{
		//String str=WebFileUtil.getSystemRootPath(request)+indexFile;
		dir=FSDirectory.open(Paths.get(WebFileUtil.getSystemRootPath(request)+indexFile));
		//���ķִ���
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
		IndexWriter writer=new IndexWriter(dir, iwc);
		return writer;
	}
	
	/**
	 * title:BlogIndex.java
	 * description:��Ӳ�������
	 * time:2019��2��9�� ����11:15:17
	 * author:jxl
	 * @param blog
	 * @throws Exception
	 */
	public void addIndex(Blog blog)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		//�������ڴ���������ͨ��IndexWriter�Բ�ͬ���ļ����������Ĵ����������䱣������������ļ��洢��λ���С�
		
		doc.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
		doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
		doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
		writer.addDocument(doc);
		writer.close();
	}
	
	/**
	 * title:BlogIndex.java
	 * description:���²�������
	 * time:2019��2��9�� ����11:15:25
	 * author:jxl
	 * @param blog
	 * @throws Exception
	 */
	public void updateIndex(Blog blog)throws Exception{
		IndexWriter writer=getWriter();
		Document doc=new Document();
		doc.add(new StringField("id",String.valueOf(blog.getId()),Field.Store.YES));
		doc.add(new TextField("title",blog.getTitle(),Field.Store.YES));
		doc.add(new StringField("releaseDate",DateUtil.formatDate(new Date(), "yyyy-MM-dd"),Field.Store.YES));
		doc.add(new TextField("content",blog.getContentNoTag(),Field.Store.YES));
		writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
		writer.close();
	}
	
	/**
	 * title:BlogIndex.java
	 * description:ɾ��ָ�����͵�����
	 * time:2019��2��9�� ����11:15:35
	 * author:jxl
	 * @param blogId
	 * @throws Exception
	 */
	public void deleteIndex(String blogId)throws Exception{
		IndexWriter writer=getWriter();
		writer.deleteDocuments(new Term("id",blogId));
		writer.forceMergeDeletes(); // ǿ��ɾ��
		writer.commit();
		writer.close();
	}
	
	/**
	 * title:BlogIndex.java
	 * description:��ѯ������Ϣ
	 * time:2019��2��9�� ����11:14:40
	 * author:jxl
	 * @param q ��ѯ����Ϣ
	 * @return
	 * @throws Exception
	 */
	public List<Blog> searchBlog(String q)throws Exception{
		//String str=WebFileUtil.getSystemRootPath(request)+indexFile;
		
		dir=FSDirectory.open(Paths.get(WebFileUtil.getSystemRootPath(request)+indexFile));
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher is=new IndexSearcher(reader);
		//���ķִ���:��Ҫ���� ���������� 
		SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();
		
		//��ϲ�ѯ
		BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
		
		QueryParser parser=new QueryParser("title",analyzer);
		Query query=parser.parse(q);
		QueryParser parser2=new QueryParser("content",analyzer);
		Query query2=parser2.parse(q);
		booleanQuery.add(query,BooleanClause.Occur.SHOULD);
		booleanQuery.add(query2,BooleanClause.Occur.SHOULD);
		
		//�Բ�ѯ�õ�����ѣ��÷���ߣ�������и�����ʾ
		QueryScorer scorer=new QueryScorer(query);  
		Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);  
		SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");
		Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
		highlighter.setTextFragmenter(fragmenter);  
		
		TopDocs hits=is.search(booleanQuery.build(), 100);
		List<Blog> blogList=new LinkedList<Blog>();
		for(ScoreDoc scoreDoc:hits.scoreDocs){
			Document doc=is.doc(scoreDoc.doc);
			Blog blog=new Blog();
			blog.setId(Integer.parseInt(doc.get(("id"))));
			blog.setReleaseDateStr(doc.get(("releaseDate")));
			String title=doc.get("title");
			String content=StringEscapeUtils.escapeHtml(doc.get("content"));
			if(title!=null){
				TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
				String hTitle=highlighter.getBestFragment(tokenStream, title);
				if(StringUtil.isEmpty(hTitle)){
					blog.setTitle(title);
				}else{
					blog.setTitle(hTitle);					
				}
			}
			if(content!=null){
				TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content)); 
				String hContent=highlighter.getBestFragment(tokenStream, content);
				if(StringUtil.isEmpty(hContent)){
					if(content.length()<=200){
						blog.setContent(content);
					}else{
						blog.setContent(content.substring(0, 200));						
					}
				}else{
					blog.setContent(hContent);					
				}
			}
			blogList.add(blog);
		}
		return blogList;
	}
}
