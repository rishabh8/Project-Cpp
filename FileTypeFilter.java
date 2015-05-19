package Media_Player;

import java.io.File;
import javax.swing.filechooser.FileFilter;

 public class FileTypeFilter extends FileFilter
            {
                
                private String extention;
                private String description;
                
                public FileTypeFilter(String extention,String description)
                {
                    this.extention=extention;
                    this.description=description;
                }
                
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory())
                        {
                            return true;
                        }
                        else
                        {
                           return f.getName().toLowerCase().endsWith(extention);
                        }
                    }

                    @Override
                    public String getDescription() {
                            return description + String.format(" (*%s)", extention);
                    }
            
               
            }