class Solution {
public:
    set<string> solve(string &s, int &i) {
        set<string> result;
        vector<set<string>> groups;
        set<string> current = {""};

        while (i < s.size() && s[i] != '}') {
            if (s[i] == ',') {
                result.insert(current.begin(), current.end());
                current = {""};
                i++;
            }
            else if (s[i] == '{') {
                i++;
                set<string> inside = solve(s, i);
                i++;

                set<string> temp;

                for (string a : current) {
                    for (string b : inside) {
                        temp.insert(a + b);
                    }
                }

                current = temp;
            }
            else {
                char ch = s[i++];
                set<string> temp;

                for (string x : current) {
                    temp.insert(x + ch);
                }

                current = temp;
            }
        }

        result.insert(current.begin(), current.end());
        return result;
    }

    vector<string> braceExpansionII(string expression) {
        int i = 0;
        set<string> ans = solve(expression, i);

        return vector<string>(ans.begin(), ans.end());
    }
};