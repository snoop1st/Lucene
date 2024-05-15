package myLuceneApp;

// tested for lucene 7.7.3 and jdk13
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.io.FileReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author Tonia Kyriakopoulou
 */
public class SearcherDemo {
    
    public SearcherDemo(){
        try{
            String indexLocation = ("index"); //define where the index is stored
            String field = "contents"; //define which field will be searched            
            
            //Access the index using indexReaderFSDirectory.open(Paths.get(index))
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation))); //IndexReader is an abstract class, providing an interface for accessing an index.
            IndexSearcher indexSearcher = new IndexSearcher(indexReader); //Creates a searcher searching the provided index, Implements search over a single IndexReader.
            indexSearcher.setSimilarity(new ClassicSimilarity());
            
            //Search the index using indexSearcher
            search(indexSearcher, field);
            
            //Close indexReader
            indexReader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * Searches the index given a specific user query.
     */
    private void search(IndexSearcher indexSearcher, String field){
        try{
            // define which analyzer to use for the normalization of user's query
            Analyzer analyzer = new EnglishAnalyzer();
            
            // create a query parser on the field "contents"
            QueryParser parser = new QueryParser(field, analyzer);
            
            // read user's query from stdin
            BufferedReader br = new BufferedReader(new FileReader("docs/queries.txt"));
            String line = br.readLine();
            Integer j = 0;
        while(line != null) {
            if (!line.matches("Q[0-9]+|///")) {
                // parse the query according to QueryParser
                Query query = parser.parse(line);
                j++;
                String queryID = "Q"+j.toString() + " ";
                //System.out.println("Searching for: " + query.toString(field));
                
                // search the index using the indexSearcher
                TopDocs results = indexSearcher.search(query, 50);
                ScoreDoc[] hits = results.scoreDocs;
                //long numTotalHits = results.totalHits;
                //System.out.println(numTotalHits + " total matching documents");

                //display results
                for(int i=0; i<hits.length; i++){
                    Document hitDoc = indexSearcher.doc(hits[i].doc);
                    System.out.println(String.format("%s\t0\t%-10s\t0\t%-10.5f\tstandard", queryID, hitDoc.get("title"), hits[i].score));                    
                }
            }
            line = br.readLine();
        }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize a SearcherDemo
     */
    public static void main(String[] args){
        SearcherDemo searcherDemo = new SearcherDemo();
    }
}
