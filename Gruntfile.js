module.exports = function (grunt) {

    // Project configuration.
    grunt.initConfig({
            pkg: grunt.file.readJSON('package.json'),

            jshint: {
                options: {
                    reporter: require('jshint-stylish') // use jshint-stylish to make our errors look and read good
                },

                // when this task is run, lint the Gruntfile and all js files in src
                build: ['Gruntfile.js', 'src/main/js/**.js']
            },

            uglify: {
                options: {
                    banner: '/*\n <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> \n*/\n'
                },
                build: {
                    files: {
                        'build/resources/generated/static/js/sic.min.js': 'src/main/js/**.js'
                    }
                }
            },

            concat: {
                dist: {
                    src: 'src/main/js/**.js',
                    dest: 'build/resources/generated/static/js/sic.js'
                }
            },

            less: {
                build: {
                    files: {
                        'build/resources/generated/static/css/sic.css': 'src/main/less/sic.less'
                    }
                }
            },

            cssmin: {
                options: {
                    banner: '/*\n <%= pkg.name %> <%= grunt.template.today("yyyy-mm-dd") %> \n*/\n'
                },
                build: {
                    files: {
                        'build/resources/generated/static/css/sic.min.css': 'build/resources/generated/static/css/sic.css'
                    }
                }
            },

            sylesheets: {
                watch: {
                    files: 'src/main/less/*.less',
                    tasks: ['less', 'cssmin']
                },

                scripts: {
                    files: 'src/main/js/**.js',
                    tasks: ['jshint', 'uglify']
                }
            },

            copy: {
                bootstrap_fonts: {
                    expand: true,
                    src: 'node_modules/bootstrap-less/fonts/**',
                    dest: 'build/resources/generated/static/fonts/',
                    flatten: true
                }
            }
        }
    );

// Load the plugin that provides the "uglify" task.
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-less');
    grunt.loadNpmTasks('grunt-contrib-cssmin');
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-copy');

// Default task(s).
    grunt.registerTask('build', ['jshint', 'concat', 'uglify', 'less', 'cssmin', 'copy']);
    grunt.registerTask('watchcss', ['less', 'cssmin', 'watch']);
    grunt.registerTask('watchjs', ['jshint', 'concat', 'uglify', 'watch']);
    grunt.registerTask('watch', ['jshint', 'concat', 'uglify', 'less', 'cssmin', 'watch']);

}
;
