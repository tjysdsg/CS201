import os
import shutil
import argparse
import tempfile

def copy(src, dst):
    if os.path.exists(dst):
        shutil.rmtree(dst)
    if os.path.isdir(src):
        shutil.copytree(src, dst)
    else:
        shutil.copy(src, dst)

def replace_in_file(file_path, pattern, subst):
    # create temp file
    fh, abs_path = tempfile.mkstemp()
    with os.fdopen(fh, 'w') as new_file:
        with open(file_path) as old_file:
            for line in old_file:
                new_file.write(line.replace(pattern, subst))
    # Remove original file
    os.remove(file_path)
    # Move new file
    shutil.move(abs_path, file_path)

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Process some integers.')
    parser.add_argument('src', metavar='src', type=str, nargs=1, help='source location')
    parser.add_argument('dst', metavar='dst', type=str, nargs=1, help='destination location')
    parser.add_argument('-n', '--assignment-name', type=str, help='assignment name')
    parser.add_argument('problems', metavar='problems',type=str, nargs='+', help='problem names')
    
    # copy template directory
    args = parser.parse_args()
    dest = os.path.join(args.dst[0], args.assignment_name)
    copy(args.src[0], dest)

    # set assignment name in the `grading.yaml` file
    replace_in_file(os.path.join(dest, 'grading.yaml'), '@ASSIGNMENT_NAME@', args.assignment_name)

    srcdir = os.path.join(dest, 'src', 'app')
    for p in args.problems:
        dest_file = os.path.join(srcdir, p + '.java')
        copy(os.path.join(srcdir, '@PROBLEM_NAME@.java'), dest_file)
        replace_in_file(dest_file, '@PROBLEM_NAME@', p)

    # remove template java file
    os.remove(os.path.join(srcdir, '@PROBLEM_NAME@.java'))
