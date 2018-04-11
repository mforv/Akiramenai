#!/usr/bin/python
# __author__ = 'mforv'
w_file = str(input())
try:
    file = open(w_file, "r")
    print("File opened successfully")
    comments = file.read()
    file.close()
    file_new = open("sorted_"+w_file, "w")
except:
    print("File reading error")
    raise
new_co = comments.split('\n')
end_s = ""
try:
    for str_a in new_co:
        tmp_co = ["<item>",str_a, "</item>\n"]
        end_s = ''.join(tmp_co)
        file_new.write(end_s)
        print(end_s)
    print("Entries: {0}".format(str(new_co.__len__())))
    print("Array has been written successfully")
    file.close()
except:
    print('File writing error')
    raise
