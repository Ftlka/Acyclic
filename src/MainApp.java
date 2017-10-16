import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class MainApp
{
    private static int flag = 0;

    public static void main(String[] args) throws IOException
    {
        int[][] kk = creatingMatrix();
        bfs(kk, 0);//where source is number of a node -1
    }

    private static Map<Integer, List<Integer>> creatingGraphFromAFile() throws IOException
    {
        //Дан файл, получить из него мапу
        Map<Integer, List<Integer>> map = new HashMap<>();

        Path ourFilePath = Paths.get("in.txt");
        List<String> arrayOfLines = new ArrayList<>(Files.readAllLines(ourFilePath));
        int amount = Integer.parseInt(arrayOfLines.get(0));

 //       StringBuilder builder = new StringBuilder();

        List<String> arrayOfNodes = new ArrayList<>();
        for (int i=1; i<=amount; i++)
        {
            String[] g = arrayOfLines.get(i).split("\\s+");
            for (int k=0; k<amount; k++)
            {
                if (g[k].equals("1"))
                    arrayOfNodes.add(Integer.toString(k+1));
                if (k==3)
                    arrayOfNodes.add("next");
            }
        }
        int counterOfNexts = 0;
        //array of nodes is String[] and we need to create smallList of integers
        for (int j=1; j<=amount; j++)
        {
            //need to add only those before next
            List<Integer> smallList = new ArrayList<Integer>();
            for (int h=counterOfNexts; h<arrayOfNodes.size(); h++)//change amount
            {
                //here we get only an element, so we need to do something to fix it
                counterOfNexts++;
                if (arrayOfNodes.get(h).equals("next"))
                    break;
                else
                {
                    Integer element = Integer.parseInt(arrayOfNodes.get(h));
                    smallList.add(element);
                }
            }
            map.put(j, smallList);
        }
        return map;
    }

    public static Boolean bfs(int adjacency_matrix[][], int source)
    {
        Queue<Integer> queue = new LinkedList<>();
        Queue<Integer> queue1 = new LinkedList<>();
        int number_of_nodes = adjacency_matrix[source].length-1;//probably all nodes if len = 4-1
        int[] visited = new int[number_of_nodes+1];//all nodes
        int element;
        List<Integer> previousElements = new ArrayList<>(number_of_nodes+1);

        visited[source] = 1;
        int counter = 1;
        queue.add(source); //must be in every bfs
        queue1.add(source);
        System.out.println(source+1);


        while (!queue.isEmpty())
        {
            element = queue.remove();
            previousElements.add(element);

            for (int i=0; i<number_of_nodes+1; i++)
            {
                if((adjacency_matrix[element][i] == 1)&&(!previousElements.contains(i)))
                {
                    counter++;
                    queue.add(i);
                    queue1.add(i);
                    //we go here when a node is like a point where two paths lead and it was never a main node
                    if (visited[i]==1)
                    {
                        List<Integer> output = new ArrayList<>();
                        output.add(i+1);
                        for(int k=0; k<previousElements.size(); k++)
                        {
                            output.add(previousElements.get(k)+1);
                        }
                        Collections.sort(output);
                        System.out.println(output);
                        flag++;
                        if (flag==1)
                            bfs(adjacency_matrix,i);
                        else
                        {
                            try(FileWriter fw = new FileWriter("out.txt"))
                            {
                                fw.write("N");
                                fw.write("\n");
                                for (int l=0; l<output.size(); l++)
                                {
                                    fw.write(output.get(l).toString());
                                    fw.write(' ');
                                }
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        return true;
                    }
                    visited[i]=1;
                    System.out.println(i+1);
                }
            }
        }
        try (FileWriter fw = new FileWriter("out.txt"))
        {
            fw.write("A");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    private static int[][] creatingMatrix() throws IOException
    {
        //bc we have an array of strings and need an array of ints
        Path input = Paths.get("in.txt");
        List<String> arrayOfLines = new ArrayList<String>(Files.readAllLines(input));
        int amount = Integer.parseInt(arrayOfLines.get(0));
        int[][] edges = new int[amount][];
        for (int i=0; i<amount; i++)
        {
            String s = arrayOfLines.get(i+1);
            String[] strings = s.split("\\s+");
            int[] ints = new int[strings.length];
            ints = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
            edges[i] = ints;
        }
        return edges;
    }

    public static void printGraph(Map<Integer, List> graph)
    {
        for (Integer s : graph.keySet()) {
            System.out.println(s + ": ");
            System.out.printf("\t");
            for (Object s1 : graph.get(s)) {
                System.out.print(s1 + " ");
            }
            System.out.println();
        }
    }

    public static ArrayList<Integer> createList(int size)
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<size; i++)
        {
            list.add(i+1);
        }
        return list;
    }

    public static int[][] createNewMatrix(int adjacency_matrix[][], List<Integer> list)
    {
     //we need to create a new matrix we shud return
        int secondMatrix[][] = adjacency_matrix;
        for (int i=0; i<list.size(); i++)
        {
            int placement = list.get(i)-1;
            secondMatrix[i]=adjacency_matrix[placement];
        }
        return secondMatrix;
    }

    public static void endOfProg(List<Integer> output)
    {
        try(FileWriter fw = new FileWriter("out.txt"))
        {
            System.out.println("we got here, that's something");
            for (int i=0; i<output.size(); i++)
            {
                System.out.print(output.get(i)+" ");
            }
            fw.write("N");
            fw.write("\n");
            for (int l=0; l<output.size(); l++)
            {
                fw.write(output.get(l).toString());
                fw.write(' ');
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static List<Integer> reverse(List<Integer> list)
    {
        List<Integer> list2 = new ArrayList<>();
        for(int i=list.size()-1; i>=0; i--)
        {
            list2.add(list.get(i));
        }
        return list2;
    }

}
