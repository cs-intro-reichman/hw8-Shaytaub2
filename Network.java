/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }
    
    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        if (name == null)
        {return null;}
        for (int i=0; i<this.userCount; i++)
        {
            if (users[i].getName().toLowerCase().equals(name.toLowerCase()))
            {
                return users[i];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (userCount==users.length-1)
        {return false;}
        if (getUser(name)!=null){
            return false;
        }
        users [userCount] = new User(name);
        this.userCount++;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1==null || name2==null){return false;}
        int user1Index = -1;
        int user2Index = -1;
        //return the user index of name1
        for (int i=0; i<this.userCount; i++){
            if (users[i].getName().toLowerCase().equals(name1.toLowerCase()))
            {
                user1Index = i;
                break;
            }
        }
        //return the user index of name2
        for (int i=0; i<this.userCount; i++){
            if (users[i].getName().toLowerCase().equals(name2.toLowerCase()))
            {
                user2Index = i;
                break;
            }
        }
        //If any of the two names is not a user in this network return false
        if (user1Index==-1 || user2Index ==-1){return false;}
        if (user1Index==user2Index){return false;}
        return users [user1Index].addFollowee(name2);
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User user = getUser(name);
        if (user == null) return null;
        String recommended = null;
        int maxMutual = 0;
        for (int i = 0; i < this.userCount; i++) {
            User other = users[i];
            if (!user.follows(other.getName()) && !user.getName().equals(other.getName())) {
                int mutualCount = user.countMutual(other);
                if (mutualCount > maxMutual) {
                    maxMutual = mutualCount;
                    recommended = other.getName();
                }
            }
        }
        return recommended;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        String mostPopular = null;
        int maxCount = 0;
        for (int i = 0; i < this.userCount; i++) {
            int count = followeeCount(users[i].getName());
            if (count > maxCount) {
                maxCount = count;
                mostPopular = users[i].getName();
            }
        }
        return mostPopular;
    }

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < this.userCount; i++) {
          if (users[i].follows(name) && users[i]!=null) 
            {
                count++;
            }
        }
        return count;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String result = "Network:";
        for (int i = 0; i < this.userCount; i++) {
            result += "\n" + users[i].toString();
        }
        return result; 
    }
}
