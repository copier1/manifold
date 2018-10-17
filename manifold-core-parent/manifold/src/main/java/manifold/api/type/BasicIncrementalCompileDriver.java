package manifold.api.type;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class BasicIncrementalCompileDriver implements IIncrementalCompileDriver
{
  private final boolean _incremental;
  private final Map<File, Set<String>> _typesToFile;

  public BasicIncrementalCompileDriver( boolean incremental )
  {
    _incremental = incremental;
    _typesToFile = new ConcurrentHashMap<>();
  }

  @Override
  public boolean isIncremental()
  {
    return _incremental;
  }

  @Override
  public Collection<File> getChangedFiles()
  {
    String manFilesProp = System.getProperty( "manifold.source.files" );
    if( manFilesProp == null )
    {
      return Collections.emptySet();
    }

    List<String> paths = new ArrayList<>();
    for( StringTokenizer tokenizer = new StringTokenizer( manFilesProp, File.pathSeparator ); tokenizer.hasMoreTokens(); )
    {
      paths.add( tokenizer.nextToken() );
    }
    return paths.stream().map( File::new ).collect( Collectors.toList() );
  }

  @Override
  public void mapTypesToFile( Set<String> set, File iFile )
  {
    _typesToFile.put( iFile, set );
  }

  public Map<File, Set<String>> getTypesToFile()
  {
    return _typesToFile;
  }
}
